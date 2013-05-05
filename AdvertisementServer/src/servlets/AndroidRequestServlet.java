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

import classesAndManagers.AdvertisementsManager;
import staticVariables.SpecialCharacters;

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
			ArrayList<BasicAdvertisement> promotions = adsManager.queryBasicPromotion(connection, minLng, maxLng, minLat, maxLat);
			if (promotions != null) {
				response.setContentType("text/plain");
				PrintWriter out = response.getWriter();
				for (BasicAdvertisement promotion : promotions) 
					out.println(promotion.getBasicPromotionString());
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
			ArrayList<BasicAdvertisement> events = adsManager.queryBasicEvent(connection, minLng, maxLng, minLat, maxLat);
			if (events != null) {
				response.setContentType("text/plain");
				PrintWriter out = response.getWriter();
				for (BasicAdvertisement event : events) 
					out.println(event.getBasicPromotionString());
			}
		}
	}
	
	/**
	 * Check for the coordinate input request. If the request is valid then input it to our db
	 * @param the inherited request
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
		
		checkForDealOutputRequest(request, response);
		checkForEventOutputRequest(request, response);
	}

}
