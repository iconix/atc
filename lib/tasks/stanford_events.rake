require 'net/http'
require 'xmlsimple'
require 'date'
require 'rubygems'
require 'nokogiri'
require 'open-uri'

def get_campus_map_address(raw_location)
	url = 'http://campus-map.stanford.edu/bldg_xml.cfm?srch='
	data = {}

	raw_location = raw_location.gsub(/[^\w\s]/, ',') # replace all other punctation with commas

	raw_array = []
	raw_location.split(',').each do |r| # split into many potential locations by comma boundaries
		r2 = r.chomp.strip
		raw_array << r2
	end

	uri_array = []

	for i in 0..2

		case i
		when 0 # turn whitespace into plusses, url escaping for &
			for r in raw_array
				l = r.downcase.squeeze(' ').strip.gsub(' ', '+').gsub("&", "%26")
				uri_array << l
			end
		when 1 # remove numbers
			uri_array_2 = []
			for u in uri_array
				u2 = u.gsub(/\d+/, '').chomp('+')
				uri_array_2 << u2
			end
			uri_array = uri_array_2
		else
			data[:error] = "failed to find street address"
			break
		end

		for u in uri_array
			xml_data = Net::HTTP.get(URI.parse(url + u))
			data = XmlSimple.xml_in(xml_data)

			break if !data.empty?
		end

		break if !data.empty?
	end


	unless data[:error]
		address = {}

		address[:street] = data['building'][0]['street'][0] + ", Stanford, CA" unless data['building'][0]['street'][0].empty?
		address[:lat] = data['building'][0]['latitude'][0] unless data['building'][0]['latitude'][0].empty?
		address[:lng] = data['building'][0]['longitude'][0] unless data['building'][0]['longitude'][0].empty?

		return address
	else
		return data
	end
end

namespace :populate do
  desc "Fill database with events from http://events.stanford.edu"
  task stanford_events: :environment do

  		if File.exist?('last_ran.txt') then
  			last = File.open('last_ran.txt', 'r')
  			last_ran = DateTime.parse([*last][0])
  			last.close
  		else
  			abort("Please create a file named 'last_ran.txt' that contains the date that this task last executed.")
  		end

		url = 'http://events.stanford.edu/xml/rss.xml'
		xml_data = Net::HTTP.get(URI.parse(url))

		data = XmlSimple.xml_in(xml_data)

		events = data['channel'][0]['item']

		events_parsed = []

		errors = File.open('no_address_errors.txt', 'w')

		events.each do |event|

			pubDate = DateTime.parse(event['pubDate'][0])

			if pubDate < last_ran then
				next
			end

			event_p = {}

			title = event['title'][0]
			desc = event['description'][0]

			desc_split = desc.match(/(Date:.+<br\/>).*(Location:.+<br\/>)(.+)/m)

			date_messy = desc_split[1]
			unless date_messy.index(':').nil? or date_messy.index('.<').nil? then
				start = date_messy.index(':') + 1
				finish = date_messy.index('.<') - 1
			else
				next
			end
			date = date_messy[start..finish].strip.squeeze(' ')

			date = DateTime.parse(date)

			location_messy = desc_split[2]
			start = location_messy.index(':') + 1
			finish = location_messy.index('<') - 1

			location = location_messy[start..finish].gsub(/\s+/, ' ').strip.squeeze(' ')

			short_desc = 'Detailed location: ' + location # full location description
			long_desc = desc_split[3].gsub(/\s+/, ' ').strip.squeeze(' ')

			# find image url

			link = event['link'][0]
			page = Nokogiri::HTML(open(link))
			image_elem = page.css('div#sidebar').css('div.image').css('img')
			image_url = 'http://events.stanford.edu' + image_elem[0]['src'] unless image_elem.empty?

			# find tags
			tags_elem = page.search("[text()*='Tags:']").first.next_element.children
			tags = tags_elem.text + ", stanford" unless tags_elem.empty?

			# find address

			address_regex_result = location.match(/[0-9]+ (\D*)[,]? ([A-Z]{2}|California)( [0-9]{5}(-[0-9]{4})?)?$/)

			address = {}

			if address_regex_result.kind_of? MatchData then
				address[:street] = address_regex_result[0]
			else
				# utilize campus-map
				res = get_campus_map_address(location)
				if res[:error] then
					errors.puts event
					errors.puts
					next
				else
					address = res
				end
			end

			event_p[:title] = title
			event_p[:start_date] = date
			event_p[:end_date] = date + Rational(2, 24) # assuming events last 2 hours...
			event_p[:short_desc] = short_desc
			event_p[:long_desc] = long_desc
			event_p[:image_url] = image_url
			event_p[:tags] = tags
			event_p[:address] = address[:street] unless address[:street].nil?
			event_p[:lat] = address[:lat] unless address[:lat].nil?
			event_p[:lng] = address[:lng] unless address[:lng].nil?

			events_parsed << event_p
		end

		errors.close

		# Rails
		business = Business.find_by_name('Stanford Campus')

		events_parsed.each do |e|
			business.deals.create!(business_id: business.id,
									isEvent: true,
									title: e[:title],
									startDate: e[:start_date],
									endDate: e[:end_date],
									shortDescription: e[:short_desc],
									longDescription: e[:long_desc],
									imageURL: e[:image_url],
									tags: e[:tags],
									address: e[:address],
									latitude: e[:lat],
									longitude: e[:lng])
		end

		output = File.open('last_ran.txt', 'w')
		last_ran = DateTime.now
		output.puts last_ran.to_s
		output.close
	end
end