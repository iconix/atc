package com.sapenguins.thecornerapp.supports;

public class TimeFrame {
	
	/**
	 * Get the display time for the ad. 
	 * @param time in format yyyy-MM-dd hh:mm:ss.0 in 24hrs/day format
	 * @return time in format MM/dd/yyyy hh:mm am/pm in 12 hr/day format
	 */
	public static String getDisplayTime(String time) {
		String day = time.substring(5, 7) + "/" + time.substring(8, 10) + "/"+ time.substring(0, 4);
		int hr = Integer.valueOf(time.substring(11, 13));
		String amPm = "am";
		if (hr >= 12) {
			amPm = "pm";
			if (hr != 12) hr = hr - 12;
		}
		if (hr == 0) hr = 12;
		String timeInDay = day + " " + hr + time.substring(13, 16) + "   " + amPm;
		return timeInDay;
	}
}
