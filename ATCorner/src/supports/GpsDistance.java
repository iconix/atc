package supports;
import java.lang.Math;

public class GpsDistance {
	public double distance;
	public double latitude;
	public double longitude;

	public GpsDistance(double latitude, double longitude, double distance) {
		distance = this.distance;
		latitude = this.latitude;
		longitude = this.longitude;
	}

	/*
	 * Calculate the distance between two coordinate points using Haversine Formula
	 */
	public static double calculateDistance(double latitude1, double longitude1, double latitude2, double longitude2){
		double r = 3959; //Radius of earth is 3,959 miles
		double conversion = 69; // 69 miles for each degree of longitude or latitude
		double delta = 0.0001;
		if (Math.abs(latitude1 - latitude2) < delta) {
			return Math.abs(longitude2 - longitude1)*conversion;
		} else if (Math.abs(longitude1 - longitude2) < delta) {
			return Math.abs(latitude2 - latitude1)*conversion;
		} else {
			double dLat = Math.toRadians(latitude2 - latitude1);
			double dLong = Math.toRadians(longitude2 - longitude1);
			double sindLat = Math.sin(dLat/2);
			double sindLong = Math.sin(dLong/2);
			double a = Math.pow(sindLat, 2);
			double b = Math.cos(latitude1)*Math.cos(latitude2)*Math.pow(sindLong, 2);
			double distance = 2*r*Math.asin(Math.pow(a+b,0.5));
			return distance;
		}
	}

	/*
	 * Find an approximate bounding box around a coordinate given a distance
	 */
	public double[] findBoundingBox(double latitude, double longitude, double distance) {
		double conversion = 69; // 69 miles for each degree of longitude or latitude
		double minLatitude = latitude - distance/conversion;
		double maxLatitude = latitude + distance/conversion;
		double minLongitude = longitude - distance/conversion;
		double maxLongitude = longitude + distance/conversion;

		double[] coordinateArray = new double[] {minLatitude, maxLatitude, minLongitude, maxLongitude};
		return coordinateArray;
	}
}