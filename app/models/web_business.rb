# == Schema Information
#
# Table name: web_businesses
#
#  id               :integer          not null, primary key
#  name             :string(255)
#  websiteURL       :string(255)
#  imageOption      :integer
#  imageURL         :string(255)
#  shortDescription :string(255)
#  longDescription  :text
#  phoneNumber      :string(255)
#  web_source       :string(255)
#  created_at       :datetime         not null
#  updated_at       :datetime         not null
#

class WebBusiness < ActiveRecord::Base
  attr_accessible :name, :websiteURL, :imageOption, :imageURL, :shortDescription, :longDescription, :phoneNumber, :web_source
  
  has_many :deals, dependent: :destroy
  
  validates :name, presence: true, length: { maximum: 100 }
  validates :web_source, presence: true
end
