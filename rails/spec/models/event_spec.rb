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

require 'spec_helper'

describe Event do
  pending "add some examples to (or delete) #{__FILE__}"
end
