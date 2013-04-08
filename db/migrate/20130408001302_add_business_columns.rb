class AddBusinessColumns < ActiveRecord::Migration
  def up
		add_column :businesses, :websiteURL, :string
		add_column :businesses, :imageOption, :integer
		add_column :businesses, :imageUrl, :string
		add_column :businesses, :imageUpload, :binary, limit: 2.megabytes
		add_column :businesses, :shortDescription, :string
		add_column :businesses, :longDescription, :text, limit: 1024
		add_column :businesses, :sundayOpenTime, :time
		add_column :businesses, :sundayCloseTime, :time
		add_column :businesses, :mondayOpenTime, :time
		add_column :businesses, :mondayCloseTime, :time
		add_column :businesses, :tuesdayOpenTime, :time
		add_column :businesses, :tuesdayCloseTime, :time
		add_column :businesses, :wednesdayOpenTime, :time
		add_column :businesses, :wednesdayCloseTime, :time
		add_column :businesses, :thursdayOpenTime, :time
		add_column :businesses, :thursdayCloseTime, :time
		add_column :businesses, :fridayOpenTime, :time
		add_column :businesses, :fridayCloseTime, :time
		add_column :businesses, :saturdayOpenTime, :time
		add_column :businesses, :saturdayCloseTime, :time
		add_column :businesses, :latitude, :decimal, precision: 15, scale: 10
		add_column :businesses, :longitude, :decimal, precision: 15, scale: 10
		add_column :businesses, :address, :text
		add_column :businesses, :phoneNumber, :integer
  end

  def down
		remove_column :businesses, :websiteURL
		remove_column :businesses, :imageOption
		remove_column :businesses, :imageUrl
		remove_column :businesses, :imageUpload
		remove_column :businesses, :shortDescription
		remove_column :businesses, :longDescription
		remove_column :businesses, :sundayOpenTime
		remove_column :businesses, :sundayCloseTime
		remove_column :businesses, :mondayOpenTime
		remove_column :businesses, :mondayCloseTime
		remove_column :businesses, :tuesdayOpenTime
		remove_column :businesses, :tuesdayCloseTime
		remove_column :businesses, :wednesdayOpenTime
		remove_column :businesses, :wednesdayCloseTime
		remove_column :businesses, :thursdayOpenTime
		remove_column :businesses, :thursdayCloseTime
		remove_column :businesses, :fridayOpenTime
		remove_column :businesses, :fridayCloseTime
		remove_column :businesses, :saturdayOpenTime
		remove_column :businesses, :saturdayCloseTime
		remove_column :businesses, :latitude
		remove_column :businesses, :longitude
		remove_column :businesses, :address
		remove_column :businesses, :phoneNumber
  end
end
