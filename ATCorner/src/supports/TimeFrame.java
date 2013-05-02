package supports;

public class TimeFrame {
	
	public static final long ONE_YEAR = 10000000000L;
	public static final long THREE_MONTHS = 300000000L;
	public static final long ONE_MONTH = 100000000L;
	public static final long ONE_WEEK = 7000000;
	public static final long ONE_DAY = 1000000;
	public static final long SIX_HOURS = 60000;
	public static final long THREE_HOURS = 30000;
	public static final long ONE_HOUR = 10000;
	public static final long FIFTEEN_MINUTES = 1500;
	public static final long FIVE_MINUTES = 500;
	
	
	/**
	 * Note what we give the time gap are strictly of the following
	 * 	one year
	 * 	three months
	 * 	one month
	 * 	one week
	 * 	one day
	 * 	six hours
	 * 	three hours
	 * 	one hour
	 * 	fifteen minutes
	 * 	five minutes
	 * If the option is chosen to be month or year, do a big round off to start at the year or month start
	 * @param originalTime
	 * @param timeGap
	 * @return the prior time
	 */
	
	public static final long computePriorTime(long originalTime, long timeGap) {
		
		int originalYear = getYear(originalTime);
		int originalMonth = getMonth(originalTime);
		int originalDay = getDay(originalTime);
		int originalHour = getHour(originalTime);
		int originalMinute = getMinute(originalTime);
		
		if (timeGap == ONE_YEAR) { //the only one we need to worry about is the february month in leap year
			if ((originalDay == 29) && (originalMonth == 2))
				return originalTime - timeGap - 1000000; //change the day to 28
			if ((originalDay == 28) && (originalMonth == 2) && ((originalYear % 4) == 1))
				return originalTime - timeGap + 1000000; //change the day to 29
		}
		
		
		if (timeGap == THREE_MONTHS) {
			return computePriorTime(computePriorTime(computePriorTime(originalTime, ONE_MONTH), ONE_MONTH), ONE_MONTH);
		}
		
		if (timeGap == ONE_MONTH) {
			if (originalMonth == 1) return originalTime - 8900000000L; // go to December last year
			if (originalMonth == 2) {
				if ((originalDay == 29) || ((originalDay == 28) && ((originalYear % 4) != 0)))
				return Long.valueOf(originalYear + "0131" + String.valueOf(originalTime).substring(8));
			}
			//for march, this is tricky
			if (originalMonth == 3) {
				if (((originalYear % 4) == 0) && (originalDay >= 29)) // leap year
					return Long.valueOf(originalYear + "0229" + String.valueOf(originalTime).substring(8));
				if (((originalYear % 4) != 0) && (originalDay >= 28)) // non leap year
					return Long.valueOf(originalYear + "0228" + String.valueOf(originalTime).substring(8));
			}
			
			//30-day months that are after months with 31 days
			if ((originalMonth == 4) || (originalMonth == 6) || (originalMonth == 9) || (originalMonth == 11))
				if (originalDay == 30) return originalTime - 100000000L + 1000000; // subtract 1 month and add in 1 day
			
			//31-day months that are after months with 30 days
			if ((originalMonth == 5) || (originalMonth == 7)|| (originalMonth == 10) || (originalMonth == 12)) {
				if (originalDay == 31) return originalTime - 101000000L; //change the day back to 30th
			}
		}
		
		if (timeGap == ONE_WEEK) {
			if (originalDay <= 7) {		
				//January then go back to December and decrease 1 year
				if (originalMonth == 1)
					return originalTime - 8876000000L;
				
				//months that follow month with 31 days
				if ((originalMonth == 2) || (originalMonth == 4) || (originalMonth == 6) || 
						(originalMonth == 8) || (originalMonth == 9) || (originalMonth == 11))
					return originalTime - 76000000; 
				
				//months that follow month with 30 days
				if ((originalMonth == 5) || (originalMonth == 7) || 
						(originalMonth == 10) || (originalMonth == 12))
					return originalTime - 77000000;
				
				//For case of March, we need to consider if the current year is a leap year or not
				if (originalMonth == 3) {
					if ((originalYear % 4) == 0) //leap year and February has 29 days
						return originalTime - 78000000;
					else return originalTime - 79000000;
				}
			}		
		}
		
		if ((timeGap == ONE_DAY) && (originalDay == 1)) 
			return computePriorTime(originalTime, ONE_WEEK) + 6000000; //add 6 days
		
		if ((timeGap == SIX_HOURS) && (originalHour < 6)) 
			return computePriorTime(originalTime, ONE_DAY) + 180000; //add 18 hours
		
		if ((timeGap == THREE_HOURS) && (originalHour < 3))
			return computePriorTime(originalTime, ONE_DAY) + 210000; //add 21 hours
		
		if ((timeGap == ONE_HOUR) && (originalHour == 1)) 
			return computePriorTime(originalTime, ONE_DAY) + 230000; //add 23 hours
		
		if ((timeGap == FIFTEEN_MINUTES) && (originalMinute < 15))  
			return computePriorTime(originalTime, ONE_HOUR) + 4500; //add 45 minutes
		
		if ((timeGap == FIVE_MINUTES) && (originalMinute < 5)) 
			return computePriorTime(originalTime, ONE_HOUR) + 5500; //add 55 minutes
		
		
		return originalTime - timeGap;
	}
	
	/**
	 * Compute the time end time
	 * @param originalTime
	 * @param timeGap
	 * @return
	 */
	public static final long computeEndTime(long originalTime, long timeGap) {
		int originalYear = getYear(originalTime);
		int originalMonth = getMonth(originalTime);
		int originalDay = getDay(originalTime);
		int originalHour = getHour(originalTime);
		int originalMinute = getMinute(originalTime);
		
		if (timeGap == ONE_YEAR) { //the only one we need to worry about is the february month in leap year
			if ((originalDay == 29) && (originalMonth == 2))
				return originalTime + timeGap - 1000000; //change the day to 28
			if ((originalDay == 28) && (originalMonth == 2) && ((originalYear % 4) == 3))
				return originalTime + timeGap + 1000000; //change the day to 29
		}
		
		
		if (timeGap == THREE_MONTHS) {
			return computeEndTime(computeEndTime(computeEndTime(originalTime, ONE_MONTH), ONE_MONTH), ONE_MONTH);
		}
		
		if (timeGap == ONE_MONTH) {
			if (originalMonth == 12) return originalTime + 8900000000L; // go to january new year
			
			//tricky here
			if (originalMonth == 1) {
				if (((originalYear % 4) == 0) && (originalDay >= 29)) // leap year
					return Long.valueOf(originalYear + "0229" + String.valueOf(originalTime).substring(8));
				if (((originalYear % 4) != 0) && (originalDay >= 28)) // non leap year
					return Long.valueOf(originalYear + "0228" + String.valueOf(originalTime).substring(8));
			}
			
			if (originalMonth == 2) {
				if ((originalDay == 29) || ((originalDay == 28) && ((originalYear % 4) != 0)))
				return Long.valueOf(originalYear + "0331" + String.valueOf(originalTime).substring(8));
			}
			
			//31-day months that are before months with 3 days
			if ((originalMonth == 3) || (originalMonth == 5) || (originalMonth == 8) || (originalMonth == 10))
				if (originalDay == 31) return originalTime + 100000000L - 1000000; // add 1 month and subtract in 1 day
			
			//30-day months that are before months with 31 days
			if ((originalMonth == 4) || (originalMonth == 6)|| (originalMonth == 9) || (originalMonth == 11)) {
				if (originalDay == 30) return originalTime + 101000000L; //change the day back to 31th
			}
		}
		
		if (timeGap == ONE_WEEK) {
			//apply timeGap = ONE_DAY seven time
			return computeEndTime(computeEndTime(computeEndTime(computeEndTime(computeEndTime(computeEndTime(computeEndTime(originalTime, ONE_DAY), ONE_DAY), ONE_DAY), ONE_DAY), ONE_DAY), ONE_DAY), ONE_DAY);
		}
		
		if (timeGap == ONE_DAY) {
			if ((originalDay == 30 && ((originalMonth == 4) || (originalMonth == 6) || (originalMonth == 9) || (originalMonth == 11)))
					|| (originalDay == 31 && ((originalMonth == 1) || (originalMonth == 3) || (originalMonth == 5) || (originalMonth == 7) || (originalMonth == 8) || (originalMonth == 10)))){
				int newMonth = originalMonth + 1;
				return Long.valueOf(originalYear + "0" + newMonth + "01" + String.valueOf(originalTime).substring(8));         //the first of next month
			}
			
			if (originalDay == 31 && originalMonth == 12) // then go to the first of next year
				return originalTime + 8900000000L - 30000000; //go next year then minus 30 days
			
			if ((originalDay == 29 && originalMonth == 2) 
					|| (originalDay == 28 && originalMonth == 2 && ((originalYear % 4) != 0))) {
				return Long.valueOf(originalYear + "0301" + String.valueOf(originalTime).substring(8));         //the first of next month
			}
		}
		
		if ((timeGap == SIX_HOURS) && (originalHour >= 18)) 
			return computeEndTime(originalTime, ONE_DAY) - 180000; //minus 18 hours
		
		if ((timeGap == THREE_HOURS) && (originalHour >= 21))
			return computeEndTime(originalTime, ONE_DAY) - 210000; //minus 21 hours
		
		if ((timeGap == ONE_HOUR) && (originalHour == 23)) 
			return computeEndTime(originalTime, ONE_DAY) - 230000; //minus 23 hours
		
		if ((timeGap == FIFTEEN_MINUTES) && (originalMinute >= 45))  
			return computeEndTime(originalTime, ONE_HOUR) - 4500; //minus 45 minutes
		
		if ((timeGap == FIVE_MINUTES) && (originalMinute >= 55)) 
			return computeEndTime(originalTime, ONE_HOUR) - 5500; //minus 55 minutes
		
		
		return originalTime + timeGap;
	}
	
	/**
	 * Get the year in a time that was written in the expression yyyyMMddHHmmss
	 * @param time
	 * @return the year
	 */
	public static final int getYear(long time) {
		//time is in format yyyyMMddHHmmss
		return Integer.valueOf((String.valueOf(time)).substring(0, 4));
	}
	
	/**
	 * Get the month in a time that was written in the expression yyyyMMddHHmmss
	 * @param time
	 * @return the month
	 */
	public static final int getMonth(long time) {
		return Integer.valueOf((String.valueOf(time)).substring(4, 6));
	}
	
	/**
	 * Get the day in a time that was written in the expression yyyyMMddHHmmss
	 * @param time
	 * @return the day
	 */
	public static final int getDay(long time) {
		return Integer.valueOf((String.valueOf(time)).substring(6, 8));
	}
	
	/**
	 * Get the hour in a time that was written in the expression yyyyMMddHHmmss
	 * @param time
	 * @return the hour
	 */
	public static final int getHour(long time) {
		return Integer.valueOf((String.valueOf(time)).substring(8, 10));
	}
	
	/**
	 * Get the minute in a time that was written in the expression yyyyMMddHHmmss
	 * @param time
	 * @return minute
	 */
	public static final int getMinute(long time) {
		return Integer.valueOf((String.valueOf(time)).substring(10, 12));
	}
	
	/**
	 * Get the second in a time that was written in the expression yyyyMMddHHmmss
	 * @param time
	 * @return second
	 */
	public static final int getSecond(long time) {
		return Integer.valueOf((String.valueOf(time)).substring(12));
	}
	
	/**
	 * Get the string expression of the day from yyyyMMddHHmmss
	 * @param time
	 * @return the time 
	 */
	public static final String getDateInString(long time) {
		String timeString = String.valueOf(time);
		String dateString = timeString.substring(4, 6) + "/" + timeString.substring(6, 8) + "/" + timeString.substring(0, 4);
		return dateString;
	}
	
	/**
	 * Get the string expression of the time from yyyyMMddHHmmss
	 * @param time
	 * @return the time 
	 */
	public static final String getTimeInString(long time) {
		String isPm = " pm";
		int hour = getHour(time);
		int minute = getMinute(time);
		if (hour > 12) hour = hour - 12;
		else isPm = " am";
		String hourStr = String.valueOf(hour);
		if (hour < 10) hourStr = "0" + hourStr;
		String minuteStr = String.valueOf(minute);
		if (minute < 10) minuteStr = "0" + minuteStr;
		String timeString = hourStr + ":" + minuteStr + isPm;
		return timeString;
	}
	
	public static final long getDateInLong(String date) {
		//TODO date will have the form mm/dd/yyyy, transform it to yyyymmdd000000 with six 0
		return 0;
	}
	
	public static final long getTimeInLong(String time) {
		//TODO time will be of the form hh:mm, transform it to hhmm00
		return 0;
	}
	
	
}
