class AddIndexToBusinessesRememberToken < ActiveRecord::Migration
  def change
    add_index  :businesses, :remember_token
  end
end
