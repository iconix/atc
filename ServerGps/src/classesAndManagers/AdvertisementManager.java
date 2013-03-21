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
	
	
	
}
