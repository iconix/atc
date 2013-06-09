class RemoveTimesFromDeals < ActiveRecord::Migration
  def up
    remove_column :deals, :startTime
    remove_column :deals, :endTime
  end

  def down
    add_column :deals, :startTime, :time
    add_column :deals, :endTime, :time
  end
end
