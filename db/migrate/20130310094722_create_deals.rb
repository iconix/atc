class CreateDeals < ActiveRecord::Migration
  def change
    create_table :deals do |t|
      t.integer :business_id
      t.decimal :lng, precision: 15, scale: 10
      t.decimal :lat, precision: 15, scale: 10
      t.datetime :start_date
      t.datetime :end_date
      t.string :title
      t.text :tags

      t.timestamps
    end
  end
end
