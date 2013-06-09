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


def offers(base_page_url, tags)
  puts "#{base_page_url}"
  
  events_parsed = []
  page = Nokogiri::HTML(open(base_page_url))
  event_items = page.css('div.card.featured')
  
  puts "#{event_items.length}"
  
  count = 1
  success_count = 1
  event_items.each do |item|
    href = item.css('div.img a')[0]['href']
    puts "\t Event #{count}: #{href}"
    event_url = href
    img = item.css('div.img a img')[0]['src']
    begin
      event_page = Nokogiri::HTML(open(event_url, HEADERS_HASH))
      puts "\t\t ...Parsing"
      event_header = event_page.css('div#detail div.main-content-details.blocks.goh-offer-details')[0]
      if not event_header then next end
      event = {}
      event[:title] = event_header.css('div.goh-title-loc-cont h1')[0].text.strip
      event[:image_url] = img
      # WHEN
      start_date = Date.today - Rational(3, 1) # 3 days ago
      start_date = start_date.to_s + "T08:00:00" # at 8am => '%Y-%m-%dT%H:%M:%S'
      end_date = Date.today + Rational(11, 1) # 11 days later 
      end_date = end_date.to_s + "T20:00:00" # at 8pm => '%Y-%m-%dT%H:%M:%S'
      event[:start_date] = start_date
      event[:end_date] = end_date
      # DESCRIPTION
      event_desc = event_page.css('div.goh-offer-details-content-left div.goh-offer-section div')[0].text.strip
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
      business_name = event_page.css('div#map-addresses div.mgoh-a-address div')[1]
      if business_name then
        event[:long_desc] += '<p>' + business_name.text.strip + "</p>"
      end
      business_website = event_page.css('div#map-addresses div.mgoh-a-address div a.mgoh-a-link')[0]
      if business_website then
        event[:long_desc] += '<p>' + business_website['href'] + "</p>"
      end
      # WHERE
      where_div = event_page.css('div#map-addresses div.mgoh-a-address div')
      addr_available = false
      where_div.each do |wh_div|
        if wh_div.text.include? "Get directions" then
          where_text = wh_div.text.gsub(/\s+/, ' ').strip.squeeze(' ')
          where_text = where_text[0, where_text.length - 14]
          where_text = where_text.gsub(/(\d{3})\s{1}\d{3}-\d{4}/, '')
          event[:address] = where_text[0, where_text.length - 14]
          addr_available = true
          break
        end
      end
      if not addr_available then next end
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
  return events_parsed
end

namespace :populate do
  task google_offers: :environment do
    #debugger
    offers = []
    
    urls = [
      'https://www.google.com/offers/special?cat=7153923286571777934&gl=US&hl=en', 
      'https://www.google.com/offers/landing?cat=food&gl=US&hl=en&qloc=Bay+Area+CA',
      'https://www.google.com/offers/landing?cat=shopping&gl=US&hl=en&qloc=Bay+Area+CA',
      'https://www.google.com/offers/landing?cat=adventure&gl=US&hl=en&qloc=Bay+Area+CA',
      'https://www.google.com/offers/landing?cat=events&gl=US&hl=en&qloc=Bay+Area+CA',
      'https://www.google.com/offers/landing?cat=travel&gl=US&hl=en&qloc=Bay+Area+CA',
      'https://www.google.com/offers/landing?cat=beauty&gl=US&hl=en&qloc=Bay+Area+CA',
      'https://www.google.com/offers/landing?cat=health&gl=US&hl=en&qloc=Bay+Area+CA',
      'https://www.google.com/offers/landing?cat=auto&gl=US&hl=en&qloc=Bay+Area+CA',
      'https://www.google.com/offers/landing?cat=services&gl=US&hl=en&qloc=Bay+Area+CA']
    urls.each do |url|
      offers += offers(url, "deal, offer, Bay Area")
    end # urls.each
    
    offers.uniq!
    puts "offers = #{offers.length}"
    
    # Write to DB (Rails)
    if true then
      business = Business.find_by_name('Bay Area')
      if not business then 
        puts "Bay Area business account is not found!" 
        return
      end
      # delete old offers
      old_offers = business.deals.find(:all, conditions: {isEvent: false})
      begin
        old_offers.each do |e|
          e.destroy
        end
      rescue Exception=>ex
        puts "DEV Exception: #{ex}"
        next
      end
      # insert new events
		  begin
		    offers.each do |e|
			    business.deals.create!(business_id: business.id,
									    isEvent: false,
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

