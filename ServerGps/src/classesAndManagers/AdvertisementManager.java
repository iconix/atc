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
		String query = "";
		try {
			Statement s = connection.createStatement();
			s.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
