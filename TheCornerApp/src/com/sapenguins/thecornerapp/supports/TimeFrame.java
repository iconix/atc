package com.sapenguins.thecornerapp.supports;

public class TimeFrame {
	
	/**
	 * Get the display time for the ad. 
	 * @param time in format yyyy-MM-dd hh:mm:ss.0 in 24hrs/day format
	 * @return time in format MM/dd/yyyy hh:mm am/pm in 12 hr/day format
	 */
	public static String getDisplayTime(String time) {
		String day = getDisplayDate(time);
		String hour = getDisplayHour(time);
		return day + "   " + hour;
	}
	
	/**
	 * Get the display time in format of hh:mm am/pm in 12 hr/day format from the 
	 * time in format yyyy-MM-dd hh:mm:ss.0 in 24hrs/day format
	 * @param time in format yyyy-MM-dd hh:mm:ss.0 in 24hrs/day format
	 * @return time in format hh:mm am/pm in 12 hr/day format
	 */
	public static String getDisplayHour(String time) {
		int hr = Integer.valueOf(time.substring(11, 13));
		String amPm = "am";
		if (hr >= 12) {
			amPm = "pm";
			if (hr != 12) hr = hr - 12;
		}
		if (hr == 0) hr = 12;
		return hr + time.substring(13, 16) + " " + amPm;
	}
	
	/**
	 * Get the day before the current display date.
	 * Keep the hours the same
	 * @param the displayDate in the format of yyyy-MM-dd
	 */
	public static String getDatePriorToCurrentDisplayDate(String displayDate) {
		String yMd[] = displayDate.split("-");
		int year = Integer.valueOf(yMd[0]);
		int month = Integer.valueOf(yMd[1]);
		int day = Integer.valueOf(yMd[2]);
		if (day > 1) {
			day = day - 1;
		} else {
			if (month == 3) {
				month = 2;
				if (year % 4 == 0) day = 29; // leap year
				else day = 28;
			} else if (month == 1) {
				year = year - 1;
				month = 12;
				day = 31;
			} else if (month == 2 || month == 4 || month == 6 || month == 8 || month == 9 || month == 11) {
				month = month - 1;
				day = 31;
			} else {
				month = month - 1;
				day = 30;
			}
		}
		
		
		String dayStr = String.valueOf(day);
		if (day < 10) dayStr = "0" + dayStr;
		String monthStr = String.valueOf(month);
		if (month < 10) monthStr = "0" + monthStr;
		
		return String.valueOf(year) + "-" + monthStr + "-" + dayStr;
	}
	
	/**
	 * Get the day after the current display date.
	 * Keep the hours the same
	 * @param the displayDate in the format of yyyy-MM-dd
	 */
	public static String getDateAfterCurrentDisplayDate(String displayDate) {
		String yMd[] = displayDate.split("-");
		int year = Integer.valueOf(yMd[0]);
		int month = Integer.valueOf(yMd[1]);
		int day = Integer.valueOf(yMd[2]);
		
		if (month == 2) {
			if (((year % 4 == 0) && (day == 29)) || ((year % 4 != 0) && (day == 28))) {
				month = 3;
				day = 1;
			} else day = day + 1;
		} else if (month == 12) {
			if (day == 31) {
				year = year + 1;
				month = 1;
				day = 1;
			} else day = day + 1;
		} else if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10) {
			if (day == 31) {
				month = month + 1;
				day = 1;
			} else day = day + 1;
		} else {
			if (day == 30) {
				month = month + 1;
				day = 1;
			} else day = day + 1;
		}	
		
		return getDate(year, month, day);
	}
	
	/**
	 * Get the display date in the following format MM/dd/yyyy from the format
	 * yyyy-MM-dd
	 * @param date in yyyy-MM-dd
	 * @return date in MM/dd/yyyy
	 */
	public static String getDisplayDate(String date) {
		return date.substring(5,7) + "/" + date.substring(8, 10) + "/" + date.substring(0, 4);
	}
	
	/**
	 * Given the year, month, and day. Return the date in the format of yyyy-MM-dd
	 * @param year
	 * @param month
	 * @param day
	 * @return date in yyyy-MM-dd
	 */
	public static String getDate(int year, int month, int day) {
		String dayStr = String.valueOf(day);
		if (day < 10) dayStr = "0" + dayStr;
		String monthStr = String.valueOf(month);
		if (month < 10) monthStr = "0" + monthStr;
		
		return String.valueOf(year) + "-" + monthStr + "-" + dayStr;
	}
}
