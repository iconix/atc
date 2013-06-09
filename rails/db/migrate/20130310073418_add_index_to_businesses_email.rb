class AddIndexToBusinessesEmail < ActiveRecord::Migration
  def change
		add_index :businesses, :email, unique: true
  end
end
