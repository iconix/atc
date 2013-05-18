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
  desc "Fill database with deals from stanford.edu domains"
  task stanford_deals: :environment do

    root_url = 'http://www.bkstr.com'
    url = 'http://www.bkstr.com/NavigationSearchDisplay/10001-530756000-10161-1?storeId=10161&navActionType=paging&resultCountPerPage0=80&sortKey=P_DEFAULTSORT0%7Casc%2BP_DEFAULTSORT1%7Casc&storeId=10161&catalogId=10001&navActionType=paging'
		doc = Nokogiri::HTML(open(url)) do |config|
      config.noblanks
    end
    
    tags = 'sale, clearance, bookstore, Stanford'
    address = 
      "STANFORD BOOKSTORE
      519 LASUEN MALL
      STANFORD, CA 94305-3003"
    location = get_campus_map_address("519 LASUEN MALL, STANFORD, CA 94305-3003")
    start_date = Date.today - Rational(3, 1) # 3 days ago
    start_date = start_date.to_s + "T08:00:00" # at 8am => '%Y-%m-%dT%H:%M:%S'
    end_date = Date.today + Rational(11, 1) # 11 days later 
    end_date = end_date.to_s + "T20:00:00" # at 8pm => '%Y-%m-%dT%H:%M:%S'
    
    count = 1
    deals_parsed = []
		doc.css('div#efProductCategory a').each do |el|
		  if el['id'] then
		    detail_url = root_url + el['href']
		    img = el.css('img')
		    #puts el['title']
		    #puts detail_url
		    #puts img[0]['src']
		  
		    deal = {}
		    deal[:title] = el['title']
		    deal[:image_url] = img[0]['src']
		    deal[:address] = address
			  deal[:lat] = location[:lat]
			  deal[:lng] = location[:lng]
			  deal[:start_date] = start_date
			  deal[:end_date] = end_date
		    
		    # deal detail page
		    detail_doc = Nokogiri::HTML(open(detail_url)) do |config|
          config.noblanks
        end
        detail_el = detail_doc.css('div.efProductDescription')
        price = detail_el.css('div.efProductPrice span')[0].text.strip
        long_desc = detail_el.css('form div#efDescriptionArea.efb1')[0].text.gsub(/\s+/, ' ').strip.squeeze(' ')
        #puts price, long_desc
        deal[:short_desc] = price
        deal[:long_desc] = long_desc
			  deal[:tags] = tags
			  
			  price = price.split('$')[1].to_f
			  if price > 4.0 then
			    deals_parsed << deal
			    puts "#{count} deals have been parsed"
			    count += 1
			  end
		  end
		end
		
		# Rails
		business = Business.find_by_name('Stanford Campus')

		deals_parsed.each do |d|
			business.deals.create!(business_id: business.id,
									isEvent: false,
									title: d[:title],
									startDate: d[:start_date],
									endDate: d[:end_date],
									shortDescription: d[:short_desc],
									longDescription: d[:long_desc],
									imageURL: d[:image_url],
									tags: d[:tags],
									address: d[:address],
									latitude: d[:lat],
									longitude: d[:lng])
		end
		
	end
end
