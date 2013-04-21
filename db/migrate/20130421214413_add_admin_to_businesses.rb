class AddAdminToBusinesses < ActiveRecord::Migration
  def change
    add_column :businesses, :admin, :boolean, default: false
  end
end
