# encoding: UTF-8
# This file is auto-generated from the current state of the database. Instead
# of editing this file, please use the migrations feature of Active Record to
# incrementally modify your database, and then regenerate this schema definition.
#
# Note that this schema.rb definition is the authoritative source for your
# database schema. If you need to create the application database on another
# system, you should be using db:schema:load, not running all the migrations
# from scratch. The latter is a flawed and unsustainable approach (the more migrations
# you'll amass, the slower it'll run and the greater likelihood for issues).
#
# It's strongly recommended to check this file into your version control system.

ActiveRecord::Schema.define(:version => 20130426030739) do

  create_table "businesses", :force => true do |t|
    t.string   "name"
    t.string   "email"
    t.string   "password_digest"
    t.string   "remember_token"
    t.string   "websiteURL"
    t.integer  "imageOption"
    t.string   "imageURL"
    t.text     "shortDescription"
    t.text     "longDescription"
    t.time     "sundayOpenTime"
    t.time     "sundayCloseTime"
    t.time     "mondayOpenTime"
    t.time     "mondayCloseTime"
    t.time     "tuesdayOpenTime"
    t.time     "tuesayCloseTime"
    t.time     "wednesdayOpenTime"
    t.time     "wednesdayCloseTime"
    t.time     "thursdayOpenTime"
    t.time     "thursdayCloseTime"
    t.time     "fridayOpenTime"
    t.time     "fridayCloseTime"
    t.time     "saturdayOpenTime"
    t.time     "saturdayCloseTime"
    t.decimal  "latitude",           :precision => 15, :scale => 10
    t.decimal  "longitude",          :precision => 15, :scale => 10
    t.text     "address"
    t.string   "phoneNumber"
    t.datetime "created_at",                                                            :null => false
    t.datetime "updated_at",                                                            :null => false
    t.boolean  "admin",                                              :default => false
  end

  add_index "businesses", ["email"], :name => "index_businesses_on_email", :unique => true
  add_index "businesses", ["remember_token"], :name => "index_businesses_on_remember_token"

  create_table "deals", :force => true do |t|
    t.integer  "business_id"
    t.integer  "web_business_id"
    t.decimal  "longitude",          :precision => 15, :scale => 10
    t.decimal  "latitude",           :precision => 15, :scale => 10
    t.datetime "startDate"
    t.datetime "endDate"
    t.string   "title"
    t.integer  "imageOption"
    t.string   "imageURL"
    t.text     "shortDescription"
    t.text     "longDescription"
    t.string   "firstTag"
    t.string   "secondTag"
    t.string   "thirdTag"
    t.boolean  "sunday"
    t.boolean  "monday"
    t.boolean  "tuesday"
    t.boolean  "wednesday"
    t.boolean  "thursday"
    t.boolean  "friday"
    t.boolean  "saturday"
    t.text     "address"
    t.boolean  "promotionOrEvent"
    t.datetime "created_at",                                         :null => false
    t.datetime "updated_at",                                         :null => false
    t.string   "image_file_name"
    t.string   "image_content_type"
    t.integer  "image_file_size"
    t.datetime "image_updated_at"
  end

	add_index "deals", ["business_id", "web_business_id", "created_at"], :name => "index_deals"

  create_table "web_businesses", :force => true do |t|
    t.string   "name"
    t.string   "websiteURL"
    t.integer  "imageOption"
    t.string   "imageURL"
    t.text     "shortDescription"
    t.text     "longDescription"
    t.string   "phoneNumber"
    t.string   "web_source"
    t.datetime "created_at",       :null => false
    t.datetime "updated_at",       :null => false
  end

end
