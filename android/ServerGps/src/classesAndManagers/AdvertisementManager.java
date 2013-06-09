package classesAndManagers;

import java.sql.*;
import java.util.ArrayList;

import staticVariables.*;

public class AdvertisementManager {

	public AdvertisementManager() {
	
	}
		
	/**
	 * Add a new advertisement to the database
	 * @param connection to the database
	 * @param advertisement instance of Advertisement
	 */
	public void addNewAdvertisement(Connection connection, Advertisement advertisement) {
        String query = "INSERT INTO " + TableName.dealsDB + 
                " VALUES(" + advertisement.businessId + ", " +
                advertisement.longitude + ", " +
                advertisement.latitude + ", " +
                advertisement.startDate + ", " +
                advertisement.endDate + ", \"" +
                advertisement.title + "\", \"" +
                advertisement.tags + "\", " +
                advertisement.creationTime + ", " +
                advertisement.updateTime + ")";
		try {
			Statement s = connection.createStatement();
			s.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Find advertisements given the AdvertisementConfig parameters
	 * @param AdvertisementConfig
	 */
	public ArrayList<Advertisement> queryAdvertisement(Connection connection, AdvertisementConfig advertisementConfig) {
		ArrayList<Advertisement> adList = new ArrayList<Advertisement>();
		String query = "select * from " + TableName.dealsDB +
				" where longitude >= " + advertisementConfig.getLowerLongitude() +
				" and longitude <= " + advertisementConfig.getHigherLongitude() +
				" and latitude >= " + advertisementConfig.getLowerLatitude() +
				" and latitude <= " + advertisementConfig.getHigherLatitude() +
				" time >= " + advertisementConfig.getStartTime() +
				" time <= " + advertisementConfig.getEndTime();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			resultSet.beforeFirst();
			while (resultSet.next()) {
				Advertisement advertisement = new Advertisement(resultSet.getInt("business_id"), resultSet.getDouble("lng"),
						resultSet.getDouble("lat"), resultSet.getLong("start_date"), resultSet.getLong("end_date"), resultSet.getString("title"),
						resultSet.getString("tags"), resultSet.getLong("created_at"), resultSet.getLong("updated_at"));
				adList.add(advertisement);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return adList;
		
	}
	
	
	
}
