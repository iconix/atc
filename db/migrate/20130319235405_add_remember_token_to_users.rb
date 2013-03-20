class AddRememberTokenToBusinesses < ActiveRecord::Migration
  def change
    add_column :businesses, :remember_token, :string
    add_index  :businesses, :remember_token
  end
end
