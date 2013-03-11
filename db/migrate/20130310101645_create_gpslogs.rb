class CreateGpslogs < ActiveRecord::Migration
  def change
    create_table :gpslogs do |t|
      t.string :device_id
      t.datetime :log_time
      t.string :longitude
      t.string :latitude
      t.boolean :is_pin
      t.integer :user_id

      t.timestamps
    end
  end
end
