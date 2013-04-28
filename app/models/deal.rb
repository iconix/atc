# == Schema Information
#
# Table name: deals
#
#  id                 :integer          not null, primary key
#  business_id        :integer
#  longitude          :decimal(15, 10)
#  latitude           :decimal(15, 10)
#  startDate          :date
#  endDate            :date
#  title              :string(255)
#  imageOption        :integer
#  imageURL           :string(255)
#  shortDescription   :string(255)
#  longDescription    :text(1024)
#  firstTag           :string(255)
#  secondTag          :string(255)
#  thirdTag           :string(255)
#  startTime          :time
#  endTime            :time
#  sunday             :boolean
#  monday             :boolean
#  tuesday            :boolean
#  wednesday          :boolean
#  thursday           :boolean
#  friday             :boolean
#  saturday           :boolean
#  address            :text
#  promotionOrEvent   :boolean
#  created_at         :datetime         not null
#  updated_at         :datetime         not null
#  image_file_name    :string(255)
#  image_content_type :string(255)
#  image_file_size    :integer
#  image_updated_at   :datetime
#

class Deal < ActiveRecord::Base
  attr_accessible :longitude, :latitude, :startDate, 
									:endDate, :title, :imageOption, :imageURL,
									:shortDescription, :longDescription, :firstTag,
									:secondTag, :thirdTag, :startTime, :endTime, :sunday,
									:monday, :tuesday, :wednesday, :thursday, :friday,
									:saturday, :address, :promotionOrEvent, :business_id, :web_business_id

	geocoded_by :address
	after_validation :geocode, :if => :address_changed?

  belongs_to :business
  belongs_to :web_businesses
  
  ACCESS_KEY_ID = 'AKIAJRSEOF4RPP2YEXPA'
  SECRET_ACCESS_KEY = '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'
  BUCKET = 'rails-img-manager'
  
  attr_accessible :image
  has_attached_file :image,
                    :storage => "s3",
                    :bucket => BUCKET,
                    :path => "uploads/:attachment/:id/:styles.:extension",
                    :styles => {
                      :medium => "300x300>",
                      :thumb => "100x100>" },
                    :s3_credentials => {
                      :access_key_id => ACCESS_KEY_ID,
                      :secret_access_key => SECRET_ACCESS_KEY },
                    :s3_permissions => "public-read"

	validates :title, presence: true
	validates :startDate, presence: true
	validates :endDate, presence: true
	#validates :longitude, presence: true
	#validates :latitude, presence: true

  default_scope order: 'deals.created_at DESC'
end
