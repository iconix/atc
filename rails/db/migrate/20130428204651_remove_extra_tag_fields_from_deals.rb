class RemoveExtraTagFieldsFromDeals < ActiveRecord::Migration
  def up
  	remove_column :deals, :secondTag
    remove_column :deals, :thirdTag
    rename_column :deals, :firstTag, :tags
  end

  def down
  	add_column :deals, :secondTag, :string
    add_column :deals, :thirdTag, :string
    rename_column :deals, :tags, :firstTag
  end
end
