# == Schema Information
#
# Table name: deals
#
#  id          :integer          not null, primary key
#  longitude   :float(255)
#  latitude    :float(255)
#  content     :string(255)
#  starttime   :datetime
#  endtime     :datetime
#  image_url   :string(255)
#  category    :string(255)
#  business_id :integer
#  created_at  :datetime         not null
#  updated_at  :datetime         not null
#

class Deal < ActiveRecord::Base
  attr_accessible :business_id, :category, :content, :endtime, :image_url, :latitude, :longitude, :starttime

	validates :business_id, presence: true
	validates :content, presence: true
	validates :starttime, presence: true
	validates :endtime, presence: true
	validates :longitude, presence: true
	validates :latitude, presence: true
end
