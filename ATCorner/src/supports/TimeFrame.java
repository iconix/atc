package supports;

public class TimeFrame {
	public static final long computePriorTime(long originalTime, long timeGap) {
		//TODO change this to compute correctly the time
		return originalTime - timeGap;
	}
	
	public static final long computeEndTime(long originalTime, long timeGap) {
		//TODO change this as well
		return originalTime + timeGap;
	}
	
	public static final int getYear(long time) {
		//TODO get year from format yyyymmddmmhhss
		return 0;
	}
	
	public static final int getMonth(long time) {
		//TODO the same
		return 0;
	}
	
	//TODO, the same for day, hour, minutes, and second, second may not be neccessary
	
	public static final long getDateInLong(String date) {
		//TODO date will have the form mm/dd/yyyy, transform it to yyyymmdd000000 with six 0
		return 0;
	}
	
	public static final long getTimeInLong(String time) {
		//TODO time will be of the form hh:mm, transform it to hhmm00
		return 0;
	}
	
	
}
