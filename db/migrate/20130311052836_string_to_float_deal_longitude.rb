class StringToFloatDealLongitude < ActiveRecord::Migration
  def self.up
		change_table :deals do |t|
			t.change :longitude, :float
		end
  end

  def self.down
		change_table :deals do |t|
			t.change :longitude, :string
		end
  end
end
