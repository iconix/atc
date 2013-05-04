namespace :db do
  desc "Add default values of 'false' to old deals"
  task is_event_defaults: :environment do
    Deal.find(:all, :conditions => {:isEvent => nil}).each do |deal| 
    	deal.update_attribute(:isEvent, false) 
    end
  end
end
