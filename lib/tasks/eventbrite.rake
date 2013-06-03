require 'net/http'
require 'xmlsimple'
require 'date'
require 'rubygems'
require 'nokogiri'
require 'open-uri'

HEADERS_HASH = {"User-Agent" => "Ruby/#{RUBY_VERSION}"}
COMMON_TAGS = "event, eventbrite"
SHORT_DESC_WORD_COUNT = 30

# events of a this month
def events_this_month()
  puts "events_this_month"
  base_url = 'http://www.eventbrite.com/'
  base_page_url = 'http://www.eventbrite.com/directory?loc=Palo+Alto&is_miles=True&city=Palo+Alto&slat=37.44&slng=-122.14&date=month&radius=60.0&lat=37.44&lng=-122.14'
  paginations = ['', '&page=2', '&page=3', '&page=4', '&page=5', '&page=6', '&page=7', '&page=8', '&page=9']
  
  events_parsed = []
  paginations.each do |p|
    puts "pagination: #{p}"
    page = Nokogiri::HTML(open(base_page_url + p))
    event_items = page.css('tr.event_listing_item.clrfix')
    
    count = 1
    success_count = 1
    event_items.each do |item|
      href = item.css('td.info h3 a')[0]['href']
      puts "\t Event #{count}: #{href}"
      event_url = href
      img = item.css('td.logo a img')[0]['src']
      begin
        event_page = Nokogiri::HTML(open(event_url, HEADERS_HASH))
      rescue Exception=>e
        puts "\t\t Error opening event URL: #{e}"
        sleep 5
      else
        puts "\t\t ...Parsing"
        event_header = event_page.css('div#contentpub div.main div#subheader table#subheader_table tbody tr td#subheader_info_cell div#event_header')[0]
        if not event_header then next end
        event = {}
        event[:title] = event_header.css('h1 span.summary')[0].text.strip
        event[:image_url] = img
        # WHEN
        start_date = nil
        end_date = nil
        when_text = event_header.css('h2')[1].text
        puts "\t\t #{when_text}"
        if when_text.include? "from" and when_text.include? "to" then
          date_text, time_text = when_text.split('from')
          start_time, end_time = time_text.split('to')
          start_date = DateTime.parse(date_text.strip + ', ' + start_time.strip)
          end_date = DateTime.parse(date_text.strip + ', ' + end_time.strip)
        elsif when_text.include? "-" and when_text.include? "at" then
          start_text, end_text = when_text.split('-')
          date_text, time_text = start_text.split('at')
          start_date = DateTime.parse(date_text.strip + ', ' + time_text.strip)
          date_text, time_text = end_text.split('at')
          end_date = DateTime.parse(date_text.strip + ', ' + time_text.strip)
        else
          next
        end
        event[:start_date] = start_date
        event[:end_date] = end_date
        # DESCRIPTION
        event_desc = event_page.css('div#contentpub div.main div#col_628 div.panel_628 div.panel_body span.description')[0].text.gsub(/\s+/, ' ').strip.squeeze(' ')
        event[:short_desc] = ""
        event[:long_desc] = event_desc
        # WHERE
        where_text = event_page.css('div#contentpub div.main div#col_280 div.panel_280 div#panel_when.panel_body h2.location.vcard')[0].text.gsub(/\s+/, ' ').strip.squeeze(' ')
        event[:address] = where_text
        # TAG
        event[:tags] = "event, eventbrite, Palo Alto"
        
        events_parsed << event
        puts event
        success_count += 1
      ensure
        count += 1
        sleep 1.0 + rand
      end  # done: begin/rescue
      
    end # done: event_items.each
  end # done: paginations.each
  return events_parsed
end

namespace :populate do
  desc "Fill database with events from http://www.eventbrite.com"
  task palo_alto_events: :environment do
    #debugger
    
    # events if this month
    events = events_this_month()
    
    events.uniq!
    puts "events = #{events.length}"
    
    # Write to DB (Rails)
    if true then
		  business = Business.find_by_name('Palo Alto')
		  events.each do |e|
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
		end
  end
end
