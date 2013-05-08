class RemoveWebBusinessesForeignKeys < ActiveRecord::Migration
  def change
  	remove_column :deals, :web_business_id if column_exists?(:deals)
  end
end
