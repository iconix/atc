# == Schema Information
#
# Table name: deals
#
#  id               :integer          not null, primary key
#  businessID       :integer
#  longitude        :decimal(15, 10)
#  latitude         :decimal(15, 10)
#  startDate        :date
#  endDate          :date
#  title            :string(255)
#  imageOption      :integer
#  imageURL         :string(255)
#  imageUpload      :binary(2097152)
#  shortDescription :string(255)
#  longDescription  :text(1024)
#  firstTag         :string(255)
#  secondTag        :string(255)
#  thirdTag         :string(255)
#  startTime        :time
#  endTime          :time
#  sunday           :boolean
#  monday           :boolean
#  tuesday          :boolean
#  wednesday        :boolean
#  thursday         :boolean
#  friday           :boolean
#  saturday         :boolean
#  address          :text
#  promotionOrEvent :boolean
#  created_at       :datetime         not null
#  updated_at       :datetime         not null
#

class Deal < ActiveRecord::Base
  attr_accessible :businessID, :longitude, :latitude, :startDate, :endDate, :title
  belongs_to :business

	validates :businessID, presence: true
	validates :title, presence: true
	validates :startDate, presence: true
	validates :endDate, presence: true
	validates :longitude, presence: true
	validates :latitude, presence: true

  default_scope order: 'deals.created_at DESC'
end
