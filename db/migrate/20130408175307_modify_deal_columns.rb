class ModifyDealColumns < ActiveRecord::Migration
  def up
		rename_column :deals, :business_id, :businessID
		rename_column :deals, :lng, :longitude
		rename_column :deals, :lat, :latitude
		rename_column :deals, :start_date, :startDate
		rename_column :deals, :end_date, :endDate
		remove_column :deals, :tags
  end

  def down
		rename_column :deals, :businessID, :business_id 
		rename_column :deals, :longitude, :lng 
		rename_column :deals, :latitude, :lat
		rename_column :deals, :startDate, :start_date
		rename_column :deals, :endDate, :end_date
		remove_column :deals, :tags
  end
end
