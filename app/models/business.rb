# == Schema Information
#
# Table name: businesses
#
#  id                 :integer          not null, primary key
#  name               :string(255)
#  email              :string(255)
#  password_digest    :string(255)
#  remember_token     :string(255)
#  websiteURL         :string(255)
#  imageOption        :integer
#  imageURL           :string(255)
#  shortDescription   :text
#  longDescription    :text
#  sundayOpenTime     :time
#  sundayCloseTime    :time
#  mondayOpenTime     :time
#  mondayCloseTime    :time
#  tuesdayOpenTime    :time
#  tuesayCloseTime    :time
#  wednesdayOpenTime  :time
#  wednesdayCloseTime :time
#  thursdayOpenTime   :time
#  thursdayCloseTime  :time
#  fridayOpenTime     :time
#  fridayCloseTime    :time
#  saturdayOpenTime   :time
#  saturdayCloseTime  :time
#  latitude           :decimal(15, 10)
#  longitude          :decimal(15, 10)
#  address            :text
#  phoneNumber        :string(255)
#  created_at         :datetime         not null
#  updated_at         :datetime         not null
#  admin              :boolean          default(FALSE)
#

class Business < ActiveRecord::Base
  attr_accessible :email, :name, :password, :password_confirmation,
									:websiteURL, :imageOption, :imageURL, :imageUpload,
									:shortDescription, :longDescription, :sundayOpenTime,
									:sundayCloseTime, :mondayOpenTime, :mondayCloseTime,
									:tuesdayOpenTime, :tuesdayCloseTime, :wednesdayOpenTime,
									:wednesdayCloseTime, :thursdayOpenTime,
									:thursdayCloseTime, :fridayOpenTime, :fridayCloseTime,
									:saturdayOpenTime, :saturdayCloseTime, :latitude,
									:longitude, :address, :phoneNumber, :image_delete

	geocoded_by :address
	after_validation :geocode, :if => :address_changed?

	has_secure_password
  	has_many :deals, dependent: :destroy

  	ACCESS_KEY_ID = 'AKIAJRSEOF4RPP2YEXPA'
	SECRET_ACCESS_KEY = '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'
	BUCKET = 'rails-img-manager'

	attr_accessible :image
	has_attached_file :image,
	                :storage => "s3",
	                :bucket => BUCKET,
	                :path => "uploads/businesses/:id/:styles.:extension",
	                :styles => {
	                  :medium => "300x300>",
	                  :thumb => "100x100>" },
	                :s3_credentials => {
	                  :access_key_id => ACCESS_KEY_ID,
	                  :secret_access_key => SECRET_ACCESS_KEY },
	                :s3_permissions => "public-read"

	before_save :destroy_image?
	before_save { |business| business.email = email.downcase }
	before_save :create_remember_token

	validates :name, presence: true, length: { maximum: 100 }
	VALID_EMAIL_REGEX = /\A[\w+\-.]+@[a-z\d\-.]+\.[a-z]+\z/i
	validates :email, presence: true, format: { with: VALID_EMAIL_REGEX }, uniqueness: { case_sensitive: false }
	validates :password, presence: true, length: { minimum: 6 }
	validates :password_confirmation, presence: true
	validates_attachment_content_type :image, :content_type => ['image/jpg', 'image/jpeg', 'image/png', 'image/gif']

	def image_delete
	  @image_delete ||= "0"
	end

	def image_delete=(value)
	  @image_delete = value
	end

	private

	def create_remember_token
		self.remember_token = SecureRandom.urlsafe_base64
	end

	def destroy_image?
	    self.image.clear if @image_delete == "1" and !image.dirty?
  	end
end
