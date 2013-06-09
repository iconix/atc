require 'json'
require 'uri'
require 'net/http'

# Only parses twice if url doesn't start with a scheme
def get_host_without_www(url)
  uri = URI.parse(url)
  uri = URI.parse("http://#{url}") if uri.scheme.nil?
  host = uri.host.downcase
  host.start_with?('www.') ? host[4..-1] : host
end

def get_parsed_json(api)
  result = Net::HTTP.get(URI.parse(api))
  JSON.parse(result)
end

namespace :db do
  desc "Fill database with 8coupons.com data"
  task eightcoupons: :environment do
    api_call = 'http://api.8coupons.com/v1/getdeals?key=c7e1014c8278e03497628327b0ae75494150691e2b12e975b40fe569e624a0e4a4745ef7ccc2fcfcf7a1e5d2307e6894&zip=94305&mileradius=20'

    parsed_eightcoupons = get_parsed_json api_call
    
    parsed_eightcoupons.each do |deal|
      business_name = deal["name"]

      business_url = deal["homepage"] # optional field
      business_url = deal["storeURL"] unless (business_url.is_a? String and !business_url.empty?)
      
      #r = 1 + rand(999)
      #business_email = "email#{r}@#{get_host_without_www(business_url)}" unless business_url.nil?
      
      #business_password = "password"
      
      # Rails call
      '''business = Business.create!(name: business_name,
                                  email: business_email,
                                  websiteURL: business_url,
                                  password: business_password,
                                  password_confirmation: business_password)'''
      business = WebBusiness.create!(name: business_name,
                                       websiteURL: business_url,
                                       web_source: "8coupons")
      
      deal_title = deal["dealTitle"]
      deal_imageURL = deal["showImage"]
      deal_startDate = deal["postDate"]
      deal_endDate = deal["expirationDate"]

      # TODO multiple options, redemption locations available in Groupon
      # defaulting to first option, first location for now

      deal_longitude = deal["lon"]
      deal_latitude = deal["lat"]
      
      # TODO strip out HTML elements?
      #deal_shortDesc = Sanitize.clean(deal["highlightsHtml"])
      #deal_longDesc = Sanitize.clean(deal["pitchHtml"])
      
      deal_shortDesc = deal["dealInfo"]
      deal_longDesc = deal["disclaimer"]
      
      # firstTag = categoryID, secondTag = subcategoryID, thirdTag = DealTypeID
      
      first_api_call = 'http://api.8coupons.com/v1/getcategory'
      first_result = Net::HTTP.get(URI.parse(first_api_call))
      first_parsed = JSON.parse(first_result)
      
      first_tag_code = deal["categoryID"]
      
      second_api_call = 'http://api.8coupons.com/v1/getsubcategory'
      second_result = Net::HTTP.get(URI.parse(second_api_call))
      second_parsed = JSON.parse(second_result)
      
      second_tag_code = deal["subcategoryID"]
      
      third_api_call = 'http://api.8coupons.com/v1/getdealtype'
      third_result = Net::HTTP.get(URI.parse(third_api_call))
      third_parsed = JSON.parse(third_result)
      
      third_tag_code = deal["DealTypeID"]
      
      deal_firstTag = first_parsed.find{|k, v| k["categoryID"] == first_tag_code }["category"] if first_parsed.find{|k, v| k["categoryID"] == first_tag_code }.is_a? Hash   # optional field
      deal_secondTag = second_parsed.find{|k, v| k["categoryID"] == second_tag_code }["category"] if second_parsed.find{|k, v| k["categoryID"] == second_tag_code }.is_a? Hash # optional field
      deal_thirdTag = third_parsed.find{|k, v| k["categoryID"] == third_tag_code }["category"] if third_parsed.find{|k, v| k["categoryID"] == third_tag_code }.is_a? Hash # optional field
      
      # address consists of multiple Groupon fields
      # (streetAddress1, streetAddress2, city, state, postalCode)
      
      address1 = deal["address"]
      address2 = deal["address2"]
      city = deal["city"]
      state = deal["state"]
      zipCode = deal["ZIP"]

      deal_address = "#{address1}\n#{address2}\n#{city}, #{state} #{zipCode}"
      deal_tags = "#{deal_firstTag}, #{deal_secondTag}, #{deal_thirdTag}"
      
      # Rails call
      business.deals.create!(web_business_id: business.id,
                             latitude: deal_latitude,
                             longitude: deal_longitude,
                             startDate: deal_startDate,
                             endDate: deal_endDate,
                             title: deal_title,
                             imageURL: deal_imageURL,
                             shortDescription: deal_shortDesc,
                             longDescription: deal_longDesc,
                             tags: deal_tags,
                             address: deal_address)
    end
  end
end
