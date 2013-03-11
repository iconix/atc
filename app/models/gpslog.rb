# == Schema Information
#
# Table name: gpslogs
#
#  id         :integer          not null, primary key
#  device_id  :string(255)
#  log_time   :datetime
#  longitude  :float(255)
#  latitude   :float(255)
#  is_pin     :boolean
#  user_id    :integer
#  created_at :datetime         not null
#  updated_at :datetime         not null
#

class Gpslog < ActiveRecord::Base
  attr_accessible :device_id, :is_pin, :latitude, :log_time, :longitude, :user_id
	
	validates :user_id, presence: true
end
