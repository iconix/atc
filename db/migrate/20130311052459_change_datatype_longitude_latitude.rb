class ChangeDatatypeLongitudeLatitude < ActiveRecord::Migration

  def self.up
		change_table :deals do |t|
			t.change :longitude, :float
		end
  end

  def self.down
		change_table :deals do |t|
			t.change :longitude, :string
		end
  end

	def self.up
    change_table :deals do |t|
      t.change :latitude, :float
    end
  end

  def self.down
    change_table :deals do |t|
      t.change :latitude, :string
    end
  end

	def self.up
    change_table :gpslogs do |t|
      t.change :longitude, :float
    end
  end

  def self.down
    change_table :gpslogs do |t|
      t.change :longitude, :string
    end
  end

  def self.up
    change_table :gpslogs do |t|
      t.change :latitude, :float
    end
  end

  def self.down
    change_table :gpslogs do |t|
      t.change :latitude, :string
    end
  end

end
