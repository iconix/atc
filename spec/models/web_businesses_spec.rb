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

require 'spec_helper'

describe WebBusinesses do
  pending "add some examples to (or delete) #{__FILE__}"
end
