package classesAndManagers;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

import staticVariables.Server;
import staticVariables.SpecialCharacters;
import support.TimeFrame;

import constants.*;
import db.Ads;

public class AdvertisementsManager {

	public AdvertisementsManager() {
		
	}
	
	/**
	 * Get the current time. This is server as the timestamp for the log
	 * @return the current time represent in the format "yyyy-MM-dd HH:mm:ss"
	 */
	private String getTimeStamp() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
		sdf.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
		return "\"" + sdf.format(new Date()) + "\"";
	}
	
	/**
	 * Query for additional info about the advertisement. The parameter will be its 
	 * unique id
	 * @param Connection to the db
	 * @param advertisement id
	 * @return An AdvertisementExtraInfo object
	 */
	public AdvertisementExtraInfo getAd (Connection connection, int id) {
		String query = "select * from " + TableNames.TABLE_DEAL + " where id = " + id;
		try {
			 Statement stmt = connection.createStatement();
	   		 ResultSet rs = stmt.executeQuery(query);
	   		 rs.beforeFirst();
	   		 if (rs.next()) {
	   			 String longDescription = rs.getString(Ads.LONG_DESCRIPTION);
	   			 if (rs.wasNull() || longDescription == null || longDescription.length() == 0 ) 
	   				 longDescription = SpecialCharacters.empty;
	   			 
	   			 String tags = rs.getString(Ads.TAG);
	   			 if (rs.wasNull() || tags == null || tags.length() == 0 ) 
	   				 tags = SpecialCharacters.empty;
	   			 
	   			 AdvertisementExtraInfo advertisement = new AdvertisementExtraInfo(
	   					 rs.getTimestamp(Ads.START_DATE).toString(),
	   					 rs.getTimestamp(Ads.END_DATE).toString(),
	   					 longDescription, 
	   					 tags,
	   					 rs.getString(Ads.ADDRESS));
	   			return advertisement;
	   		 }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	/**
	 * Query for start and end time of an event/deal
	 * @param Connection to the db
	 * @param advertisement id
	 * @return An AdvertisementExtraInfo object
	 */
	public String[] getStartAndEndTime (Connection connection, int id) {
		String query = "select * from " + TableNames.TABLE_DEAL + " where id = " + id;
		try {
			 Statement stmt = connection.createStatement();
	   		 ResultSet rs = stmt.executeQuery(query);
	   		 rs.beforeFirst();
	   		 if (rs.next())
	   			 return new String[] {rs.getString(Ads.START_DATE), rs.getString(Ads.END_DATE)};
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
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
		String time = getTimeStamp();
		String query = "select * from " + TableNames.TABLE_DEAL + " where isEvent = 1 and endDate > " + time;
		return queryBasicAdvertisement(connection, query);
	}
	
	/**
	 * Query for basic information of all the events
	 * @param connection
	 * @return an array list of all the events' basic information
	 */
	public ArrayList<BasicAdvertisement> queryBasicEvent(Connection connection,
			String minLng, String maxLng, String minLat, String maxLat) {
		String time = getTimeStamp();
		String query = "select * from " + TableNames.TABLE_DEAL
				+ " where isEvent = 1 and longitude > " + minLng 
				+ " and longitude < " + maxLng + " and latitude > " + minLat
				+ " and latitude < " + maxLat
				+ " and endDate > " + time
				+ " order by startDate";
		return queryBasicAdvertisement(connection, query);
	}
	
	/**
	 * Query for basic information of all the events in a given category
	 * @param connection
	 * @return an array list of all the events' basic information
	 */
	public ArrayList<BasicAdvertisement> queryBasicEvent(Connection connection,
			String minLng, String maxLng, String minLat, String maxLat, String category) {
		String time = getTimeStamp();
		String query = "select * from " + TableNames.TABLE_DEAL
				+ " where isEvent = 1 and longitude > " + minLng 
				+ " and longitude < " + maxLng + " and latitude > " + minLat
				+ " and latitude < " + maxLat
				+ " and endDate > " + time;
		if (category.equals("Entertainment")) {
			query += " and " + EventTags.preparingSqlQuery(EventTags.Entertainment);
		} else if (category.equals("Education")) {
			query += " and " + EventTags.preparingSqlQuery(EventTags.Educational);
		} else if (category.equals("Service")) {
			query += " and " + EventTags.preparingSqlQuery(EventTags.Service);
		} else if (category.equals("Organization")) {
			query += " and " + EventTags.preparingSqlQuery(EventTags.Organization);
		} else if (category.equals("Stanford")) {
			query += " and " + EventTags.preparingSqlStanfordQuery();
		}
		query += " order by startDate";
		return queryBasicAdvertisement(connection, query);
	}
	
	/**
	 * Query for basic information of all the promotions
	 * @param connection
	 * @return an array list of all the promotions' basic information
	 */
	public ArrayList<BasicAdvertisement> queryBasicPromotion(Connection connection) {
		String time = getTimeStamp();
		String query = "select * from " + TableNames.TABLE_DEAL + " where isEvent = 0 and endDate > " + time;
		return queryBasicAdvertisement(connection, query);
	}
	
	/**
	 * Query for basic information of all the promotions in a given category
	 * @param connection
	 * @return an array list of all the promotions' basic information
	 */
	public ArrayList<BasicAdvertisement> queryBasicPromotion(Connection connection,
			String minLng, String maxLng, String minLat, String maxLat, String category) {
		String time = getTimeStamp();
		String query = "select * from " + TableNames.TABLE_DEAL
				+ " where isEvent = 0 and longitude > " + minLng 
				+ " and longitude < " + maxLng + " and latitude > " + minLat
				+ " and latitude < " + maxLat
				+ " and endDate > " + time;
		if (category.equals("Entertainment")) {
			query += " and " + PromotionTags.preparingSqlQuery(PromotionTags.Entertainment);
		} else if (category.equals("Shopping")) {
			query += " and " + PromotionTags.preparingSqlQuery(PromotionTags.Shopping);
		} else if (category.equals("Food")) {
			query += " and " + PromotionTags.preparingSqlQuery(PromotionTags.Food);
		} 
		query += " order by startDate";
		return queryBasicAdvertisement(connection, query);
	}
	
	/**
	 * Query for basic information of all the promotions in a given category
	 * @param connection
	 * @return an array list of all the promotions' basic information
	 */
	public ArrayList<BasicAdvertisement> queryBasicMapPromotion(Connection connection,
			String minLng, String maxLng, String minLat, String maxLat, String time) {
		String endTime = "\"" + TimeFrame.getDateAfterCurrentDisplayDate(time.substring(1, 11)) + " 00:00:00\"";
		String query = "select * from " + TableNames.TABLE_DEAL
				+ " where isEvent = 0 and longitude > " + minLng 
				+ " and longitude < " + maxLng + " and latitude > " + minLat
				+ " and latitude < " + maxLat
				+ " and endDate > " + time
				+ " and startDate < " + endTime; //motivation is that we want event that start within today or in the past
		query += " order by startDate";
		return queryBasicAdvertisement(connection, query);
	}
	
	/**
	 * Query for basic information of all the events in a given category
	 * @param connection
	 * @return an array list of all the events' basic information
	 */
	public ArrayList<BasicAdvertisement> queryBasicMapEvent(Connection connection,
			String minLng, String maxLng, String minLat, String maxLat, String time) {
		String endTime = "\"" + TimeFrame.getDateAfterCurrentDisplayDate(time.substring(1, 11)) + " 00:00:00\"";
		String query = "select * from " + TableNames.TABLE_DEAL
				+ " where isEvent = 1 and longitude > " + minLng 
				+ " and longitude < " + maxLng + " and latitude > " + minLat
				+ " and latitude < " + maxLat
				+ " and endDate > " + time
				+ " and startDate < " + endTime; //motivation is that we want event that start within today or in the past
		query += " order by startDate";
		return queryBasicAdvertisement(connection, query);
	}
	

	/**
	 * Query for basic information of all the promotions
	 * @param connection
	 * @return an array list of all the promotions' basic information
	 */
	public ArrayList<BasicAdvertisement> queryBasicFavorite(Connection connection, String pastOrOngoing, String[] ids) {
		if (ids != null && ids.length > 0) {
			String idConstraint = "( ";
			for (int i = 0; i < ids.length; i++) {
				if (i != (ids.length - 1))
					idConstraint += "id = " + ids[i] + " or ";
				else idConstraint += "id = " + ids[i] + ")";
			}
			String time = getTimeStamp();
			String additionalConstraint = " and endDate > " + time;
			if (pastOrOngoing.equals("isNotOnGoing")) additionalConstraint =  " and endDate < " + time;
			String query = "select * from " + TableNames.TABLE_DEAL + " where " + idConstraint + additionalConstraint;
			return queryBasicAdvertisement(connection, query);
		}
		return null;
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
		String time = getTimeStamp();
		String query = "select * from " + TableNames.TABLE_DEAL 
				+ " where isEvent = 0 and longitude > " + minLng 
				+ " and longitude < " + maxLng + " and latitude > " + minLat
				+ " and latitude < " + maxLat
				+ " and endDate > " + time;
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
	   				 //check if user input image. Do this by look at image_file_name
	   				 String image_file_name = rs.getString(Ads.IMAGE_FILE_NAME);
	   				 if (!rs.wasNull() && imageUrl != null && imageUrl.length() != 0 ) {
	   					 //image content type will be after the period
	   					 int index = image_file_name.indexOf('.');
	   					 
	   					 String image_type = image_file_name.substring(index + 1);
	   					 imageUrl = Server.IMAGE_URL_HEAD + id + "/mediums." + image_type;
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


