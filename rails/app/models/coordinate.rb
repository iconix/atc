class Coordinate < ActiveRecord::Base
  attr_accessible :latitude, :longitude, :time

  set_table_name 'coordinate'
end
