class AddIndexToDeals < ActiveRecord::Migration
  def change
		add_index :deals, [:business_id, :created_at]
  end
end
