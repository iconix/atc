package servlets;

import classesAndManagers.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import staticVariables.SpecialCharacters;
import support.TimeFrame;
import constants.TableNames;

import classesAndManagers.AdvertisementsManager;

import db.DBConnection;

/**
 * Servlet implementation class AndroidRequestServlet
 */
@WebServlet("/AndroidRequestServlet")
public class AndroidRequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    Connection connection;
    AdvertisementsManager adsManager;
    
	@Override
	public void init() throws ServletException {
		connection = DBConnection.getConnection();
		adsManager = new AdvertisementsManager();	
	}
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AndroidRequestServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}
	
	/**
	 * Check for deals output request. Respond the list of all deals 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void checkForDealOutputRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String dealRequest = (String) request.getParameter("deal");
		String minLng = (String) request.getParameter("minLng");
		String maxLng = (String) request.getParameter("maxLng");
		String minLat = (String) request.getParameter("minLat");
		String maxLat = (String) request.getParameter("maxLat");
		if (dealRequest != null && minLng != null && maxLng != null && minLat != null && maxLat != null) {		
			ArrayList<BasicAdvertisement> promotions = adsManager.queryBasicPromotion(connection, minLng, maxLng, minLat, maxLat, dealRequest);
			if (promotions != null) {
				response.setContentType("text/plain");
				PrintWriter out = response.getWriter();
				for (BasicAdvertisement promotion : promotions) 
					out.println(promotion.getBasicAdString());
			}
		}
	}
	
	
	/**
	 * Check for events output request. Respond the list of all events 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void checkForEventOutputRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String eventRequest = (String) request.getParameter("event");
		String minLng = (String) request.getParameter("minLng");
		String maxLng = (String) request.getParameter("maxLng");
		String minLat = (String) request.getParameter("minLat");
		String maxLat = (String) request.getParameter("maxLat");
		if (eventRequest != null && minLng != null && maxLng != null && minLat != null && maxLat != null) {		
			ArrayList<BasicAdvertisement> events;
			if (eventRequest.equals("All")) {
				events = adsManager.queryBasicEvent(connection, minLng, maxLng, minLat, maxLat);
			} else  {
				events = adsManager.queryBasicEvent(connection, minLng, maxLng, minLat, maxLat, eventRequest);
			}
			
			if (events != null) {
				response.setContentType("text/plain");
				PrintWriter out = response.getWriter();
				for (BasicAdvertisement event : events) 
					out.println(event.getBasicAdString());
			}
		}
	}
	
	/**
	 * Check for events output request. Respond the list of all events 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void checkForMapEventOutputRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String eventTime = (String) request.getParameter("mapevent");
		String minLng = (String) request.getParameter("minLng");
		String maxLng = (String) request.getParameter("maxLng");
		String minLat = (String) request.getParameter("minLat");
		String maxLat = (String) request.getParameter("maxLat");
		if (eventTime != null && minLng != null && maxLng != null && minLat != null && maxLat != null) {		
			ArrayList<BasicAdvertisement> events;
			events = adsManager.queryBasicMapEvent(connection, minLng, maxLng, minLat, maxLat, eventTime);
			if (events != null) {
				response.setContentType("text/plain");
				PrintWriter out = response.getWriter();
				for (BasicAdvertisement event : events) 
					out.println(event.getBasicAdString());
			}
				
				/*String endTime = "\"" + TimeFrame.getDateAfterCurrentDisplayDate(eventTime.substring(1, 10)) + " 00:00:00\"";
				String query = "select * from " + TableNames.TABLE_DEAL
						+ " where isEvent = 0 and longitude > " + minLng 
						+ " and longitude < " + maxLng + " and latitude > " + minLat
						+ " and latitude < " + maxLat
						+ " and endDate > " + eventTime
						+ " and startDate < " + endTime;
				out.println(query);*/
		}
	}
	
	/**
	 * Check for events output request. Respond the list of all events 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void checkForMapPromotionOutputRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String promotionTime = (String) request.getParameter("mapdeal");
		String minLng = (String) request.getParameter("minLng");
		String maxLng = (String) request.getParameter("maxLng");
		String minLat = (String) request.getParameter("minLat");
		String maxLat = (String) request.getParameter("maxLat");
		if (promotionTime != null && minLng != null && maxLng != null && minLat != null && maxLat != null) {		
			ArrayList<BasicAdvertisement> promotions;
			promotions = adsManager.queryBasicMapPromotion(connection, minLng, maxLng, minLat, maxLat, promotionTime);
			if (promotions != null) {
				response.setContentType("text/plain");
				PrintWriter out = response.getWriter();
				for (BasicAdvertisement promotion : promotions) 
					out.println(promotion.getBasicAdString());
			}
		}
	}
	
	/**
	 * Check for specific ad request. If the request is valid, then respond
	 * with the result found
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void checkForAdRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String adRequestId = (String) request.getParameter("adRequestId");
		if (adRequestId != null) {
			AdvertisementExtraInfo adInfo = adsManager.getAd(connection, Integer.valueOf(adRequestId));
			if (adInfo != null) {
				response.setContentType("text/plain");
				PrintWriter out = response.getWriter();
				out.print(adInfo.getAdsInfoInString());
			}
		}
	}
	
	/**
	 * Check for specific ad request. If the request is valid, then respond
	 * with the result found
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void checkForTimeOutputRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String timeRequestId = (String) request.getParameter("timeRequestId");
		if (timeRequestId != null) {
			String[] times = adsManager.getStartAndEndTime(connection, Integer.valueOf(timeRequestId));
			if (times != null && times.length == 2) {
				response.setContentType("text/plain");
				PrintWriter out = response.getWriter();
				out.print(times[0] + SpecialCharacters.delimiter + times[1]);
			}
		}
	}
	
	/**
	 * Check for specific ad request. If the request is valid, then respond
	 * with the result found
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void checkForFavoriteRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String pastOrOngoing = (String) request.getParameter("favorite");
		String ids = (String) request.getParameter("id");
		if (pastOrOngoing != null && ids != null) {		
			String[] myIds = ids.split(",");		
			ArrayList<BasicAdvertisement> favorites = adsManager.queryBasicFavorite(connection, pastOrOngoing, myIds);
			if (favorites != null) {
				response.setContentType("text/plain");
				PrintWriter out = response.getWriter();
				for (BasicAdvertisement favorite : favorites) 
					out.println(favorite.getBasicAdString());
			}
		}
	}
	
	/**
	 * Check for the coordinate input request. If the request is valid then input it to our db
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void checkForCoordinateInputRequest(HttpServletRequest request) {
		String longitude = request.getParameter("longitude");
		String latitude = request.getParameter("latitude");
		if (longitude != null && latitude != null) {
			String query = "insert into coordinate (longitude, latitude) values (" + longitude + ", " + latitude + ")";
			try {
				Statement stmt = connection.createStatement();
				stmt.executeUpdate(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		checkForCoordinateInputRequest(request);
		
		checkForTimeOutputRequest(request, response);
		checkForDealOutputRequest(request, response);
		checkForEventOutputRequest(request, response);
		checkForMapEventOutputRequest(request, response);
		checkForMapPromotionOutputRequest(request, response);
		checkForAdRequest(request, response);
		checkForFavoriteRequest(request, response);
	}

}
