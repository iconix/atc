class CreateDeals < ActiveRecord::Migration
  def change
    create_table :deals do |t|
      t.integer 	:business_id
      t.decimal 	:longitude, precision: 15, scale: 10
      t.decimal 	:latitude, precision: 15, scale: 10
      t.date 			:startDate
      t.date 			:endDate
      t.string 		:title
			t.integer		:imageOption
			t.string		:imageURL
			t.string		:shortDescription
			t.text			:longDescription, limit: 1024
			t.string		:firstTag
			t.string		:secondTag
			t.string		:thirdTag
			t.time			:startTime
			t.time			:endTime
			t.boolean		:sunday
			t.boolean		:monday
			t.boolean		:tuesday
			t.boolean		:wednesday
			t.boolean		:thursday
			t.boolean		:friday
			t.boolean		:saturday
			t.text			:address
			t.boolean		:promotionOrEvent

      t.timestamps
    end
  end
end
