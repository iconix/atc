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
#  imageUpload        :binary(2097152)
#  shortDescription   :string(255)
#  longDescription    :string(1024)
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
#  phoneNumber        :integer
#  created_at         :datetime         not null
#  updated_at         :datetime         not null
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
									:longitude, :address, :phoneNumber

	has_secure_password
  has_many :deals, dependent: :destroy

	before_save { |business| business.email = email.downcase }
	before_save :create_remember_token

	validates :name, presence: true, length: { maximum: 50 }
	VALID_EMAIL_REGEX = /\A[\w+\-.]+@[a-z\d\-.]+\.[a-z]+\z/i
	validates :email, presence: true, format: { with: VALID_EMAIL_REGEX }, uniqueness: { case_sensitive: false }
	validates :password, presence: true, length: { minimum: 6 }
	validates :password_confirmation, presence: true

	private

	def create_remember_token
		self.remember_token = SecureRandom.urlsafe_base64
	end
end
