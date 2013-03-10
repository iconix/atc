class AddPasswordDigestToBusinesses < ActiveRecord::Migration
  def change
    add_column :businesses, :password_digest, :string
  end
end
