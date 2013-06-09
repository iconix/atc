class AddAttachmentImageToBusinesses < ActiveRecord::Migration
  def self.up
    change_table :businesses do |t|
      t.attachment :image
    end
  end

  def self.down
    drop_attached_file :businesses, :image
  end
end
