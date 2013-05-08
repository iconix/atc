# == Schema Information
#
# Table name: deals
#
#  id                 :integer          not null, primary key
#  business_id        :integer
#  longitude          :decimal(15, 10)
#  latitude           :decimal(15, 10)
#  startDate          :datetime
#  endDate            :datetime
#  title              :string(255)
#  imageURL           :string(255)
#  shortDescription   :text
#  longDescription    :text
#  tags               :string(255)
#  address            :text
#  isEvent            :boolean          default(FALSE)
#  created_at         :datetime         not null
#  updated_at         :datetime         not null
#  image_file_name    :string(255)
#  image_content_type :string(255)
#  image_file_size    :integer
#  image_updated_at   :datetime
#

class Deal < ActiveRecord::Base
  attr_accessible :longitude, :latitude, :startDate, 
									:endDate, :title, :imageURL,
									:shortDescription, :longDescription, :tags,
									:startTime, :endTime, :address, :isEvent, :business_id, 
									:image_delete

	geocoded_by :address
	after_validation :geocode, :if => :address_changed?

  belongs_to :business
  
  ACCESS_KEY_ID = 'AKIAJRSEOF4RPP2YEXPA'
  SECRET_ACCESS_KEY = '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'
  BUCKET = 'rails-img-manager'
  
  attr_accessible :image
  has_attached_file :image,
                    :storage => "s3",
                    :bucket => BUCKET,
                    :path => "uploads/deals/:id/:styles.:extension",
                    :styles => {
                      :medium => "300x300>",
                      :thumb => "100x100>" },
                    :s3_credentials => {
                      :access_key_id => ACCESS_KEY_ID,
                      :secret_access_key => SECRET_ACCESS_KEY },
                    :s3_permissions => "public-read"

  before_save :destroy_image?

	validates :title, presence: true
	validates :startDate, presence: true
	validates :endDate, presence: true
  validates_attachment_content_type :image, :content_type => ['image/jpg', 'image/jpeg', 'image/png', 'image/gif']
	#validates :longitude, presence: true
	#validates :latitude, presence: true

  default_scope order: 'deals.created_at DESC'

def image_delete
  @image_delete ||= "0"
end

def image_delete=(value)
  @image_delete = value
end

private
  def destroy_image?
    self.image.clear if @image_delete == "1" and !image.dirty?
  end
end
