# == Schema Information
#
# Table name: gpslogs
#
#  id         :integer          not null, primary key
#  device_id  :string(255)
#  log_time   :datetime
#  longitude  :float(255)
#  latitude   :float(255)
#  is_pin     :boolean
#  user_id    :integer
#  created_at :datetime         not null
#  updated_at :datetime         not null
#

require 'spec_helper'

describe Gpslog do
	before { @gpslog = Gpslog.new(user_id: "1", device_id: "android#123", log_time:"15/03/2003 04:30:12", longitude:"12345", latitude:"67890", is_pin:"yes") }

  subject { @gpslog }

	describe "when user_id is not present" do
    before { @gpslog.user_id = " " }
    it { should_not be_valid }
  end
end
