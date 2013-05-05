package classesAndManagers;

import java.sql.*;
import java.util.*;

import staticVariables.Server;
import staticVariables.SpecialCharacters;

import constants.*;
import db.Ads;

public class AdvertisementsManager {

	public AdvertisementsManager() {
		
	}
	
	public ArrayList<Advertisement> queryEvent(Connection connection) {
		ArrayList<Advertisement> advertisements = new ArrayList<Advertisement>();
		String query = "select * from " + TableNames.TABLE_DEAL + " where isEvent = 1";
		try {
			 Statement stmt = connection.createStatement();
    		 ResultSet rs = stmt.executeQuery(query);
    		 rs.beforeFirst();
    		 if (rs.next()) {
    			//TODO query event in here
    		 }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return advertisements;
	}
	
	
	
	/**
	 * Query for basic information of all the events inside a coordinate bound
	 * @param connection
	 * @param min longitude
	 * @param max longitude
	 * @param min latitude
	 * @param max latitude
	 * @return an array list of all the events' basic information
	 */
	public ArrayList<BasicAdvertisement> queryBasicEvent(Connection connection) {
		String query = "select * from " + TableNames.TABLE_DEAL + " where isEvent = 1";
		return queryBasicAdvertisement(connection, query);
	}
	
	/**
	 * Query for basic information of all the events
	 * @param connection
	 * @return an array list of all the events' basic information
	 */
	public ArrayList<BasicAdvertisement> queryBasicEvent(Connection connection,
			String minLng, String maxLng, String minLat, String maxLat) {
		String query = "select * from " + TableNames.TABLE_DEAL
				+ " where isEvent = 1 and longitude > " + minLng 
				+ " and longitude < " + maxLng + " and latitude > " + minLat
				+ " and latitude < " + maxLat;
		return queryBasicAdvertisement(connection, query);
	}
	
	/**
	 * Query for basic information of all the promotions
	 * @param connection
	 * @return an array list of all the promotions' basic information
	 */
	public ArrayList<BasicAdvertisement> queryBasicPromotion(Connection connection) {
		String query = "select * from " + TableNames.TABLE_DEAL + " where isEvent = 0";
		return queryBasicAdvertisement(connection, query);
	}
	
	/**
	 * Query for basic information of all the promotions inside the coordinate bound
	 * @param connection
	 * @param min longitude
	 * @param max longitude
	 * @param min latitude
	 * @param max latitude
	 * @return an array list of all the promotions' basic information
	 */
	public ArrayList<BasicAdvertisement> queryBasicPromotion(Connection connection, 
			String minLng, String maxLng, String minLat, String maxLat) {
		String query = "select * from " + TableNames.TABLE_DEAL 
				+ " where isEvent = 0 and longitude > " + minLng 
				+ " and longitude < " + maxLng + " and latitude > " + minLat
				+ " and latitude < " + maxLat;
		return queryBasicAdvertisement(connection, query);
	}
	
	/**
	 * Get the list of advertisements' basic information from the DB that match the query
	 * @param connection to DB
	 * @param query string
	 */
	private ArrayList<BasicAdvertisement> queryBasicAdvertisement(Connection connection, String query) {
		ArrayList<BasicAdvertisement> advertisements = new ArrayList<BasicAdvertisement>();
		try {
			 Statement stmt = connection.createStatement();
   		 ResultSet rs = stmt.executeQuery(query);
   		 rs.beforeFirst();
   		 while (rs.next()) {
   			 int id = rs.getInt(Ads.ID);
   			 
   			 String shortDescription = rs.getString(Ads.SHORT_DESCRIPTION);
   			 if (rs.wasNull() || shortDescription == null || shortDescription.length() == 0 ) 
   				 shortDescription = SpecialCharacters.empty;
   			 
   			 String imageUrl = rs.getString(Ads.IMAGE_URL);
   			 if (rs.wasNull() || imageUrl == null || imageUrl.length() == 0 ) 
   				 imageUrl = SpecialCharacters.empty;
   			 
   			 if (imageUrl.equals(SpecialCharacters.empty)) {
   				 //check if user input image. Do this by look at image_content_type
   				 String image_content_type = rs.getString(Ads.IMAGE_CONTENT_TYPE);
   				 if (!rs.wasNull() && imageUrl != null && imageUrl.length() != 0 ) {
   					 //image content type will be of the form image/png
   					 //so just get the substring from 6th index
   					 image_content_type = image_content_type.substring(6);
   					 imageUrl = Server.IMAGE_URL_HEAD + id + "/" + "originals." + image_content_type;
   				 }
   			 }
   			 
   			 BasicAdvertisement advertisement = new BasicAdvertisement(id, 
   					 rs.getString(Ads.TITLE),
   					 rs.getDouble(Ads.LATITUDE), 
   					 rs.getDouble(Ads.LONGITUDE),
   					 imageUrl, 
   					 shortDescription);
   			 advertisements.add(advertisement);
   		 }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return advertisements;
	}
}


