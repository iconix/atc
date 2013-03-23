class CreateEvents < ActiveRecord::Migration
  def change
    create_table :events do |t|
      t.integer :business_id
      t.decimal :lng, :precision => 15, :scale => 10
      t.decimal :lat, :precision => 15, :scale => 10
      t.datetime :start_date
      t.datetime :end_date
      t.string :title
      t.string :content
      t.text :tags

      t.timestamps
    end
		add_index :events, [:business_id, :created_at]
  end
end
