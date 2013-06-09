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
#  imageURL           :string(255)
#  shortDescription   :text
#  longDescription    :text
#  latitude           :decimal(15, 10)
#  longitude          :decimal(15, 10)
#  address            :text
#  phoneNumber        :string(255)
#  created_at         :datetime         not null
#  updated_at         :datetime         not null
#  admin              :boolean          default(FALSE)
#  image_file_name    :string(255)
#  image_content_type :string(255)
#  image_file_size    :integer
#  image_updated_at   :datetime
#

class Business < ActiveRecord::Base
  attr_accessible :email, :name, :password, :password_confirmation,
									:websiteURL, :imageURL, :imageUpload,
									:shortDescription, :longDescription, :latitude,
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
	before_save :remove_tildes
	before_save :empty_to_nil

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

		def remove_tildes
			self.name = self.name.gsub("~", "-")
			if self.address
				self.address = self.address.gsub("~", "-")
			end
		end

		def empty_to_nil
			if self.websiteURL.blank? then
				self.websiteURL = nil
			end
			if self.imageURL.blank? then
				self.imageURL = nil
			end
			if self.shortDescription.blank? then
				self.shortDescription = nil
			end
			if self.address.blank? then
				self.address = nil
			end
			if self.phoneNumber.blank? then
				self.phoneNumber = nil
			end
			if self.image_file_name.blank? then
				self.image_file_name = nil
				self.image_content_type = nil
				self.image_file_size = nil
				self.image_updated_at = nil
			end
		end
end
