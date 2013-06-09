class RemoveWeekdayColumns < ActiveRecord::Migration
  def change
  	remove_column :businesses, :tuesayCloseTime #misspelled

  	remove_column :businesses, :sundayOpenTime
  	remove_column :businesses, :sundayCloseTime
  	remove_column :businesses, :mondayOpenTime
  	remove_column :businesses, :mondayCloseTime
  	remove_column :businesses, :tuesdayOpenTime
  	remove_column :businesses, :wednesdayOpenTime
  	remove_column :businesses, :wednesdayCloseTime
  	remove_column :businesses, :thursdayOpenTime
  	remove_column :businesses, :thursdayCloseTime
  	remove_column :businesses, :fridayOpenTime
  	remove_column :businesses, :fridayCloseTime
  	remove_column :businesses, :saturdayOpenTime
  	remove_column :businesses, :saturdayCloseTime

  	remove_column :deals, :sunday
  	remove_column :deals, :monday
  	remove_column :deals, :tuesday
  	remove_column :deals, :wednesday
  	remove_column :deals, :thursday
  	remove_column :deals, :friday
  	remove_column :deals, :saturday

  	remove_column :businesses, :imageOption
  	remove_column :deals, :imageOption
  end
end
