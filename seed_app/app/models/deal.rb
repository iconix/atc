# == Schema Information
#
# Table name: deals
#
#  id          :integer          not null, primary key
#  business_id :integer
#  lng         :decimal(15, 10)
#  lat         :decimal(15, 10)
#  start       :datetime
#  end         :datetime
#  title       :string(255)
#  tags        :text
#  created_at  :datetime         not null
#  updated_at  :datetime         not null
#

class Deal < ActiveRecord::Base
  attr_accessible :business_id, :end_date, :lat, :lng, :start_date, :tags, :title
  belongs_to :business

  default_scope order: 'deals.created_at DESC'
end
