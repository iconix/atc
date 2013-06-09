require 'net/http'
require 'xmlsimple'
require 'date'
require 'rubygems'
require 'nokogiri'
require 'open-uri'

HEADERS_HASH = {"User-Agent" => "Ruby/#{RUBY_VERSION}"}
COMMON_TAGS = "event, eventbrite"
SHORT_DESC_LENGTH = 230
SHORT_DESC_WORD_COUNT = 30

# events of a this month
def events_this_month(base_page_url, tags)
  puts "events_this_month"
  
  # Find pagination number
  begin
    page = Nokogiri::HTML(open(base_page_url))
    search_result = page.css('section#search_results h6')
    number_events = search_result[0].text.split('of')[1].to_i
    number_paginations = number_events / 10
    if number_events % 10 != 0 then number_paginations += 1 end
    puts "number_events = #{number_events}, number_paginations = #{number_paginations}"
    paginations = ['']
    for p in 2..number_paginations do 
      paginations << "&page=#{p}"
    end
    puts paginations
  rescue Exception=>e
    puts "DEV Exception: #{e}"
    return []
  end
  
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
        elsif when_text.include? "at" then
          date_text, time_text = when_text.split('at')
          start_date = DateTime.parse(date_text.strip + ', ' + time_text.strip)
          end_date = start_date + Rational(2, 24) # assuming 2-hour events
        else
          next
        end
        event[:start_date] = start_date
        event[:end_date] = end_date
        # DESCRIPTION
        event_desc = event_page.css('div#contentpub div.main div#col_628 div.panel_628 div.panel_body span.description')[0].text.strip
        paragraphs = event_desc.split(/\r?\n/)
        event[:long_desc] = ""
        paragraphs.each do |para|
          event[:long_desc] += '<p>' + para.gsub(/\s+/, ' ').strip.squeeze(' ') + '</p>'
        end
        # find a non-empty paragraph
        non_empty = 0
        while non_empty < paragraphs.length and paragraphs[non_empty].length < 5 do 
          non_empty += 1 
        end
        event[:short_desc] = ""
        if non_empty < paragraphs.length then
          short_desc = paragraphs[non_empty].gsub(/\s+/, ' ').strip.squeeze(' ')
          if short_desc.length < SHORT_DESC_LENGTH then
            event[:short_desc] = '<p>' + short_desc + '</p>'
          else
            short_desc_words = short_desc.split
            min_number = [SHORT_DESC_WORD_COUNT, short_desc_words.length].min
            for i in 0..(min_number-2) do
              if event[:short_desc].length + short_desc_words[i].length < SHORT_DESC_LENGTH then
                event[:short_desc] += short_desc_words[i] + " "
              else 
                break 
              end
            end
            event[:short_desc] += "..."
          end
        end
        # WHERE
        where_text = event_page.css('div#contentpub div.main div#col_280 div.panel_280 div#panel_when.panel_body h2.location.vcard')[0].text.gsub(/\s+/, ' ').strip.squeeze(' ')
        event[:address] = where_text
        # TAG
        event[:tags] = tags
        
        events_parsed << event
        puts event
        success_count += 1
      rescue Exception=>e
        puts "\t\t DEV Exception: #{e}"
        sleep 5
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
  task eventbrite_bayarea_events: :environment do
    #debugger
    events = []
    
    # Bay Area searched from eventbrite.com
    bayarea_url = 'http://www.eventbrite.com/directory?loc=Bay+Area%2C+CA&is_miles=True&spellcheck=0&slat=37.71&slng=-122.25&date=month&radius=60&lat=37.71&lng=-122.25'
    events += events_this_month(bayarea_url, "event, Bay Area")
    
    # Other cities
    if false then
      cities = ['Menlo Park', 'Redwood City', 'San Mateo']
      base_url_month = 'http://www.eventbrite.com/directory?date=month&city='
      cities.each do |city|
        word1, word2 = city.split
        events += events_this_month(base_url_month + word1 + '+' + word2, "event, #{city}")
      end # cities.each
    end
    
    events.uniq!
    puts "events = #{events.length}"
    
    # Write to DB (Rails)
    if true then
      business = Business.find_by_name('Bay Area')
      if not business then 
        puts "Bay Area business account is not found!" 
        return
      end
      # delete old events
      old_events = business.deals.find(:all, conditions: {isEvent: true})
      begin
        old_events.each do |e|
          e.destroy
        end
      rescue Exception=>ex
        puts "DEV Exception: #{ex}"
        next
      end
      # insert new events
		  begin
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
		    end # events.each
		  rescue Exception=>exp
        puts "DEV Exception: #{ex}"
        next
      end
		end # if
  end # environment
end

