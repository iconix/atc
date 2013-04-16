package classesAndManagers;

import java.io.OutputStream;
import java.sql.*;
import java.util.*;

import constants.*;

public class PromotionsManager {
	
	public static final String DEAL_ID = "id";
	public static final String BUSINESS_ID = "business_id";
	public static final String LATITUDE = "latitude";
	public static final String LONGITUDE = "longitude";
	public static final String START_DATE = "startDate";
	public static final String END_DATE = "endDate";
	public static final String TITLE = "title";
	public static final String IMAGE_OPTION = "imageOption";
	public static final String IMAGE_URL = "imageUrl";
	public static final String IMAGE_UPLOAD = "imageUpload";
	public static final String SHORT_DESCRIPTION = "shortDescription";
	public static final String LONG_DESCRIPTION = "longDescription";
	public static final String FIRST_TAG = "firstTag";
	public static final String SECOND_TAG = "secondTag";
	public static final String THIRD_TAG = "thirdTag";
	public static final String START_TIME = "startTime";
	public static final String END_TIME = "endTime";
	public static final String MONDAY = "monday";
	public static final String TUESDAY = "tuesday";
	public static final String WEDNESDAY = "wednesday";
	public static final String THURSDAY = "thursday";
	public static final String FRIDAY = "friday";
	public static final String SATURDAY = "saturday";
	public static final String SUNDAY = "sunday";
	public static final String ADDRESS = "address";
	public static final String PROMOTION_OR_EVENT = "promotionOrEvent";
	public static final String CREATED_AT = "created_at";
	public static final String UPDATED_AT = "updated_at";

	public PromotionsManager() {
		
	}
	
	public ArrayList<Promotion> queryPromotion(Connection connection) {
		ArrayList<Promotion> promotions = new ArrayList<Promotion>();
		String query = "select * from " + TableNames.TABLE_DEAL;
		try {
			 Statement stmt = connection.createStatement();
    		 ResultSet rs = stmt.executeQuery(query);
    		 rs.beforeFirst();
    		 while (rs.next()) {
    			 Promotion promotion = new Promotion(rs.getString(DEAL_ID), 
    					 rs.getString(BUSINESS_ID),
    					 rs.getDouble(LATITUDE), rs.getDouble(LONGITUDE), 
    					 rs.getString(START_DATE), rs.getString(END_DATE),
    					 rs.getString(TITLE), rs.getInt(IMAGE_OPTION), 
    					 rs.getString(IMAGE_URL), rs.getBinaryStream(IMAGE_UPLOAD),
    					 rs.getString(SHORT_DESCRIPTION), rs.getString(LONG_DESCRIPTION),
    					 rs.getString(FIRST_TAG), rs.getString(SECOND_TAG), 
    					 rs.getString(THIRD_TAG), rs.getString(START_TIME), 
    					 rs.getString(END_TIME), rs.getBoolean(SUNDAY), 
    					 rs.getBoolean(MONDAY), rs.getBoolean(TUESDAY), 
    					 rs.getBoolean(WEDNESDAY), rs.getBoolean(THURSDAY),
    					 rs.getBoolean(FRIDAY), rs.getBoolean(SATURDAY), 
    					 rs.getString(ADDRESS), rs.getBoolean(PROMOTION_OR_EVENT),
    					 rs.getString(CREATED_AT), rs.getString(UPDATED_AT));
    			 promotions.add(promotion);
    		 }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return promotions;
	}
}
