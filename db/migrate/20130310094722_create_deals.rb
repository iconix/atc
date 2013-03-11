class CreateDeals < ActiveRecord::Migration
  def change
    create_table :deals do |t|
      t.string :longitude
      t.string :latitude
      t.string :content
      t.datetime :starttime
      t.datetime :endtime
      t.string :image_url
      t.string :category
      t.integer :business_id

      t.timestamps
    end
  end
end
