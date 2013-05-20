class CreateCoordinates < ActiveRecord::Migration
  def change
    create_table :coordinate do |t|
      t.decimal :latitude,           :precision => 15, :scale => 10
      t.decimal :longitude,          :precision => 15, :scale => 10
      t.datetime :time

      t.timestamps
    end
  end
end
