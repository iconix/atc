namespace :db do
  desc "Remove deals associated with old web_businesses table"
  task remove_web_deals: :environment do
    Deal.find(:all, :conditions => { :business_id => nil }).each do |deal|
    	deal.destroy
    end
  end
end