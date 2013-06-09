class AddAttachmentImageToDeals < ActiveRecord::Migration
  def self.up
    change_table :deals do |t|
      t.attachment :image
    end
  end

  def self.down
    drop_attached_file :deals, :image
  end
end
