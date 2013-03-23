class AddIndexToEvents < ActiveRecord::Migration
  def change
		add_index :events, [:business_id, :created_at]
  end
end
