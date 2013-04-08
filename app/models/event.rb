# == Schema Information
#
# Table name: events
#
#  id          :integer          not null, primary key
#  business_id :integer
#  lng         :decimal(15, 10)
#  lat         :decimal(15, 10)
#  start_date  :datetime
#  end_date    :datetime
#  title       :string(255)
#  content     :string(255)
#  tags        :text
#  created_at  :datetime         not null
#  updated_at  :datetime         not null
#

class Event < ActiveRecord::Base
  attr_accessible :business_id, :content, :end_date, :lat, :lng, :start_date, :tags, :title
end
