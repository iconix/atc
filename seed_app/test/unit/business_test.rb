# == Schema Information
#
# Table name: businesses
#
#  id              :integer          not null, primary key
#  email           :string(255)
#  business_name   :string(255)
#  password_digest :string(255)
#  created_at      :datetime         not null
#  updated_at      :datetime         not null
#

require 'test_helper'

class BusinessTest < ActiveSupport::TestCase
  # test "the truth" do
  #   assert true
  # end
end
