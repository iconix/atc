class AddDefaultValueToIsEvent < ActiveRecord::Migration
  def up
  	change_column_default :deals, :isEvent, false
  end

  def down
  	change_column_default :deals, :isEvent, nil
  end
end
