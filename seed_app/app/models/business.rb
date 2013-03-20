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

class Business < ActiveRecord::Base
  attr_accessible :business_name, :email, :password_digest
  has_many :deals
end