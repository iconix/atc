class CreateWebBusinesses < ActiveRecord::Migration
  def change
    create_table :web_businesses do |t|
      t.string :name
      t.string :websiteURL
      t.integer :imageOption
      t.string :imageURL
      t.string :shortDescription
      t.text :longDescription
      t.string :phoneNumber
      t.string :web_source

      t.timestamps
    end
  end
end
