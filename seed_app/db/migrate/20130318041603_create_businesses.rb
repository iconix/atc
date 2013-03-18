class CreateBusinesses < ActiveRecord::Migration
  def change
    create_table :businesses do |t|
      t.string :email
      t.string :business_name
      t.string :password_digest

      t.timestamps
    end
  end
end
