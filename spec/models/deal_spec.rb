# == Schema Information
#
# Table name: deals
#
#  id                 :integer          not null, primary key
#  business_id        :integer
#  longitude          :decimal(15, 10)
#  latitude           :decimal(15, 10)
#  startDate          :datetime
#  endDate            :datetime
#  title              :string(255)
#  imageOption        :integer
#  imageURL           :string(255)
#  shortDescription   :text
#  longDescription    :text
#  tags               :string(255)
#  sunday             :boolean
#  monday             :boolean
#  tuesday            :boolean
#  wednesday          :boolean
#  thursday           :boolean
#  friday             :boolean
#  saturday           :boolean
#  address            :text
#  isEvent            :boolean          default(FALSE)
#  created_at         :datetime         not null
#  updated_at         :datetime         not null
#  image_file_name    :string(255)
#  image_content_type :string(255)
#  image_file_size    :integer
#  image_updated_at   :datetime
#

require 'spec_helper'

describe Deal do
	before do
    @deal = Deal.new(content: "Example Deal", business_id: "1", longitude:"12345", latitude: "67890", starttime: "Mar 12, 2013", endtime:"Mar 15, 2013", category: "food", image_url: "http://image/1")
  end

  subject { @deal }

  it { should be_valid }

  describe "when content is not present" do
    before { @deal.content = " " }
    it { should_not be_valid }
  end

	describe "when business_id is not present" do
    before { @deal.business_id = " " }
    it { should_not be_valid }
  end

	describe "when starttime is not present" do
    before { @deal.starttime = " " }
    it { should_not be_valid }
  end

	describe "when endtime is not present" do
    before { @deal.endtime = " " }
    it { should_not be_valid }
  end

	describe "when longitude is not present" do
    before { @deal.longitude = " " }
    it { should_not be_valid }
  end

	describe "when latitude is not present" do
    before { @deal.latitude = " " }
    it { should_not be_valid }
  end

end
