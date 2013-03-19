# == Schema Information
#
# Table name: deals
#
#  id          :integer          not null, primary key
#  business_id :integer
#  lng         :decimal(15, 10)
#  lat         :decimal(15, 10)
#  start_date  :datetime
#  end_date    :datetime
#  title       :string(255)
#  tags        :text
#  created_at  :datetime         not null
#  updated_at  :datetime         not null
#

class Deal < ActiveRecord::Base
  attr_accessible :business_id, :lng, :lat, :start_date, :end_date, :title, :tags
  belongs_to :business

	validates :business_id, presence: true
	validates :title, presence: true
	validates :start_date, presence: true
	validates :end_date, presence: true
	validates :lng, presence: true
	validates :lat, presence: true

  default_scope order: 'deals.created_at DESC'
end
