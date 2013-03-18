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

require 'test_helper'

class DealTest < ActiveSupport::TestCase
  # test "the truth" do
  #   assert true
  # end
end
