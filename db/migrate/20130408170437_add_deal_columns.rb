class AddDealColumns < ActiveRecord::Migration
  def up
		add_column :deals, :imageOption, :integer
		add_column :deals, :imageURL, :integer
		add_column :deals, :imageUpload, :binary, limit: 2.megabytes
		add_column :deals, :shortDescription, :string
		add_column :deals, :longDescription, :text, limit: 1024
		add_column :deals, :firstTag, :string
		add_column :deals, :secondTag, :string
		add_column :deals, :thirdTag, :string
		add_column :deals, :startTime, :time
		add_column :deals, :endTime, :time
		add_column :deals, :sunday, :boolean
		add_column :deals, :monday, :boolean
		add_column :deals, :tuesday, :boolean
		add_column :deals, :wednesday, :boolean
		add_column :deals, :thursday, :boolean
		add_column :deals, :friday, :boolean
		add_column :deals, :saturday, :boolean
		add_column :deals, :address, :text
		add_column :deals, :promotionOrEvent, :boolean
  end

  def down
		remove_column :deals, :imageOption
		remove_column :deals, :imageURL
		remove_column :deals, :imageUpload
		remove_column :deals, :shortDescription
		remove_column :deals, :longDescription
		remove_column :deals, :firstTag
		remove_column :deals, :secondTag
		remove_column :deals, :thirdTag
		remove_column :deals, :startTime
		remove_column :deals, :endTime
		remove_column :deals, :sunday
		remove_column :deals, :monday
		remove_column :deals, :tuesday
		remove_column :deals, :wednesday
		remove_column :deals, :thursday
		remove_column :deals, :friday
		remove_column :deals, :saturday
		remove_column :deals, :address
		remove_column :deals, :promotionOrEvent
  end
end
