require 'rubygems'
require 'json'
#require 'sanitize'
require 'uri'

# Only parses twice if url doesn't start with a scheme
def get_host_without_www(url)
  uri = URI.parse(url)
  uri = URI.parse("http://#{url}") if uri.scheme.nil?
  host = uri.host.downcase
  host.start_with?('www.') ? host[4..-1] : host
end

def get_parsed_json
  f = open("./groupon.json")
  json = f.read
  JSON.parse(json)
end

namespace :db do
  desc "Fill database with sample data"
  task groupon: :environment do
    parsed = get_parsed_json

    parsed["deals"].each do |deal|
      # check that deal has physical redemption location
      unless deal["options"][0]["redemptionLocations"][0].nil? then
        business_name = deal["merchant"]["name"]

        business_url = deal["merchant"]["websiteUrl"] # optional field
        
        # TODO what is business_email if business_url is nil?
        
        business_email = "email@#{get_host_without_www(business_url)}" unless business_url.nil?
        
        business_password = "password"
        
        # Rails call
        business = Business.create!(name: business_name,
                                    email: business_email,
                                    websiteURL: business_url,
                                    password: "password",
                                    password_confirmation: "password")
        
        deal_title = deal["title"]
        deal_imageURL = deal["largeImageUrl"]
        deal_startDate = deal["startAt"]
        deal_endDate = deal["endAt"]

        # TODO multiple options, redemption locations available in Groupon
        # defaulting to first option, first location for now

        deal_longitude = deal["options"][0]["redemptionLocations"][0]["lng"]
        deal_latitude = deal["options"][0]["redemptionLocations"][0]["lat"]
        
        # TODO strip out HTML elements?
        #deal_shortDesc = Sanitize.clean(deal["highlightsHtml"])
        #deal_longDesc = Sanitize.clean(deal["pitchHtml"])
        
        deal_shortDesc = deal["highlightsHtml"]
        deal_longDesc = deal["pitchHtml"]
        
        deal_firstTag = deal["tags"][0]["name"] unless deal["tags"][0].nil?
        deal_secondTag = deal["tags"][1]["name"] unless deal["tags"][1].nil? # optional field
        deal_thirdTag = deal["tags"][2]["name"] unless deal["tags"][2].nil? # optional field
        
        # address consists of multiple Groupon fields
        # (streetAddress1, streetAddress2, city, state, postalCode)
        
        address1 = deal["options"][0]["redemptionLocations"][0]["streetAddress1"]
        address2 = deal["options"][0]["redemptionLocations"][0]["streetAddress2"]
        city = deal["options"][0]["redemptionLocations"][0]["city"]
        state = deal["options"][0]["redemptionLocations"][0]["state"]
        zipCode = deal["options"][0]["redemptionLocations"][0]["postalCode"]

        deal_address = "#{address1}\n#{address2}\n#{city}, #{state} #{zipCode}"
        
        # Rails call
        business.deals.create!(business_id: business.id,
                               latitude: deal_latitude,
                               longitude: deal_longitude,
                               startDate: deal_startDate,
                               endDate: deal_endDate,
                               title: deal_title,
                               imageURL: deal_imageURL,
                               shortDescription: deal_shortDesc,
                               longDescription: deal_longDesc,
                               firstTag: deal_firstTag,
                               secondTag: deal_secondTag,
                               thirdTag: deal_thirdTag,
                               address: deal_address)

        # puts for testing
        '''p "business name" if business_name.nil?
        p "business url" if business_url.nil?
        p business_email
        p "deal title" if deal_title.nil?
        p "deal imageURL" if deal_imageURL.nil?
        p "deal startDate" if deal_startDate.nil?
        p "deal endDate" if deal_endDate.nil?
        p "deal latitude" if deal_latitude.nil?
        p "deal longitude" if deal_longitude.nil?
        p "deal shortDesc" if deal_shortDesc.nil?
        p "deal longDesc" if deal_longDesc.nil?
        p "deal firstTag" if deal_firstTag.nil?
        p "deal secondTag" if deal_secondTag.nil?
        p "deal thirdTag" if deal_thirdTag.nil?
        p "deal address\n\n" if deal_address.nil?

        p "BUSINESS\n#{business_name} #{business_url} #{business_email}"
        p "TITLE: #{deal_title}\nIMAGE URL: #{deal_imageURL}\nVALID: #{deal_startDate}-#{deal_endDate}\nCOORDINATES: #{deal_latitude}, #{deal_longitude}\nSHORT DESC: #{deal_shortDesc}\nLONG DESC: #{deal_longDesc}\nTAGS: #{deal_firstTag}, #{deal_secondTag}, #{deal_thirdTag}\nADDRESS: #{deal_address}\n\n"'''
        
        
      end
    end
  end
end
