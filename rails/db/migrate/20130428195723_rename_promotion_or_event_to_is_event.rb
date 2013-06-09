class RenamePromotionOrEventToIsEvent < ActiveRecord::Migration
  def up
  	rename_column :deals, :promotionOrEvent, :isEvent
  end

  def down
  	rename_column :deals, :isEvent, :promotionOrEvent
  end
end
