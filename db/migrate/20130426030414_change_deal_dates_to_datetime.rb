class ChangeDealDatesToDatetime < ActiveRecord::Migration
  def up
    change_column :deals, :startDate, :datetime
    change_column :deals, :endDate, :datetime
  end

  def down
    change_column :deals, :startDate, :date
    change_column :deals, :endDate, :date
  end
end
