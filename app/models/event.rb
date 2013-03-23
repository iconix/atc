class Event < ActiveRecord::Base
  attr_accessible :business_id, :content, :end_date, :lat, :lng, :start_date, :tags, :title
end
