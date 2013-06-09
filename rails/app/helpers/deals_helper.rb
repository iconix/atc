module DealsHelper
	# Convert date time to something much more readable
	def display_time(utc_time)
		day = utc_time[5..6] + "/" + utc_time[8..9] + "/" + utc_time[0..3]
		hour = utc_time[11..12].to_i
		am_or_pm = "am"
		if hour >= 12 then
			am_or_pm = "pm"
		end
		if hour == 0 then
			hour = 12
		end
		return day + " " + hour.to_s + utc_time[13..15] + " " + am_or_pm
	end
end
