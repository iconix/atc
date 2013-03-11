class StringToFloatGpslogLongitude < ActiveRecord::Migration
  def self.up
    change_table :gpslogs do |t|
      t.change :longitude, :float
    end
  end

  def self.down
    change_table :gpslogs do |t|
      t.change :longitude, :string
    end
  end
end
