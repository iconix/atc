class StringToFloatDealLatitude < ActiveRecord::Migration
  def self.up
    change_table :deals do |t|
      t.change :latitude, :float
    end
  end

  def self.down
    change_table :deals do |t|
      t.change :latitude, :string
    end
  end
end
