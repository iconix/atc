# == Schema Information
#
# Table name: businesses
#
#  id                 :integer          not null, primary key
#  name               :string(255)
#  email              :string(255)
#  password_digest    :string(255)
#  remember_token     :string(255)
#  websiteURL         :string(255)
#  imageURL           :string(255)
#  shortDescription   :text
#  longDescription    :text
#  tuesayCloseTime    :time
#  latitude           :decimal(15, 10)
#  longitude          :decimal(15, 10)
#  address            :text
#  phoneNumber        :string(255)
#  created_at         :datetime         not null
#  updated_at         :datetime         not null
#  admin              :boolean          default(FALSE)
#  image_file_name    :string(255)
#  image_content_type :string(255)
#  image_file_size    :integer
#  image_updated_at   :datetime
#

require 'spec_helper'

describe Business do
	before { @business = Business.new(name: "Example Business", email: "business@example.com", password: "foobar", password_confirmation: "foobar") }

  subject { @business }

  it { should respond_to(:name) }
  it { should respond_to(:email) }
	it { should respond_to(:password_digest) }
	it { should respond_to(:password) }
  it { should respond_to(:password_confirmation) }

	it { should be_valid }

	describe "when password is not present" do
  	before { @business.password = @business.password_confirmation = " " }
  	it { should_not be_valid }
	end
	describe "when password doesn't match confirmation" do
  	before { @business.password_confirmation = "mismatch" }
  	it { should_not be_valid }
	end

	describe "when password confirmation is nil" do
  	before { @business.password_confirmation = nil }
  	it { should_not be_valid }
	end

  describe "when name is not present" do
    before { @business.name = " " }
    it { should_not be_valid }
  end

	describe "when email is not present" do
    before { @business.email = " " }
    it { should_not be_valid }
  end

	describe "when name is too long" do
    before { @business.name = "a" * 51 }
    it { should_not be_valid }
  end

	describe "when email format is invalid" do
    it "should be invalid" do
      addresses = %w[user@foo,com user_at_foo.org example.user@foo.
                     foo@bar_baz.com foo@bar+baz.com]
      addresses.each do |invalid_address|
        @business.email = invalid_address
        @business.should_not be_valid
      end      
    end
  end

  describe "when email format is valid" do
    it "should be valid" do
      addresses = %w[user@foo.COM A_US-ER@f.b.org frst.lst@foo.jp a+b@baz.cn]
      addresses.each do |valid_address|
        @business.email = valid_address
        @business.should be_valid
      end      
    end
  end

	describe "when email address is already taken" do
    before do
      business_with_same_email = @business.dup
      business_with_same_email.save
    end

    it { should_not be_valid }
  end
end
