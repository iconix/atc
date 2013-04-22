class CreateBusinesses < ActiveRecord::Migration
  def change
    create_table :businesses do |t|
      t.string 		:name
      t.string 		:email
			t.string		:password_digest
			t.string		:remember_token
			t.string		:websiteURL
			t.integer		:imageOption
			t.string		:imageURL
			t.string		:shortDescription
			t.text		  :longDescription
			t.time			:sundayOpenTime
			t.time			:sundayCloseTime
			t.time			:mondayOpenTime
			t.time			:mondayCloseTime
			t.time			:tuesdayOpenTime
			t.time			:tuesayCloseTime
			t.time			:wednesdayOpenTime
			t.time			:wednesdayCloseTime
			t.time			:thursdayOpenTime
			t.time			:thursdayCloseTime
			t.time			:fridayOpenTime
			t.time			:fridayCloseTime
			t.time			:saturdayOpenTime
			t.time			:saturdayCloseTime
			t.decimal		:latitude, precision: 15, scale: 10
			t.decimal		:longitude, precision: 15, scale: 10
			t.text			:address
			t.string		:phoneNumber
			
      t.timestamps
    end
  end
end
