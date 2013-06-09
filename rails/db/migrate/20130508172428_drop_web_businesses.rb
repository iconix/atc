class DropWebBusinesses < ActiveRecord::Migration
  def up
  	drop_table :web_businesses if table_exists?(:web_businesses)
  end

  def down
  	create_table :web_businesses do |t|
      t.string :name
      t.string :websiteURL
      t.integer :imageOption
      t.string :imageURL
      t.text :shortDescription
      t.text :longDescription
      t.string :phoneNumber
      t.string :web_source

      t.timestamps
    end
  end
end
