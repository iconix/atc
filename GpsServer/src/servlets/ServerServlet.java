package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import classesAndManagers.*;

import java.sql.*;
import java.util.ArrayList;

import db.*;
import staticVariables.*;

/**
 * Servlet implementation class ServerServlet
 */
@WebServlet("/ServerServlet")
public class ServerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection connection;
	AccountsManager accountsManager;
	GpsCoordinateManager gpsCoordinateManager;
	PinLocationManager pinLocationManager;
	
	@Override
	public void init() throws ServletException {
		connection = DBConnection.getConnection();
		accountsManager = new AccountsManager();
		gpsCoordinateManager = new GpsCoordinateManager();
		pinLocationManager = new PinLocationManager();	
	}

	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * Check for login in request. If the server notice a request to login in from client.
	 * It will first check if it is a valid account. If it is not, then throw an error message
	 * back to the client. If it is a valid account, it will check whether this device has already
	 * register under this account. If not, then create new account to include this device
	 * @param inherited request from the doPost
	 * @param inherited response from doPost
	 */
	private void checkForLoginRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String loginUserID = (String) request.getParameter(RequestParameters.LOGIN_ACCOUNT_ID);
		String loginUserPassword = (String) request.getParameter(RequestParameters.LOGIN_ACCOUNT_PASSWORD);
		String loginDeviceID = (String) request.getParameter(RequestParameters.LOGIN_DEVICE_ID);
		if (loginUserID != null && loginUserPassword != null && loginDeviceID != null) {
			response.setContentType("text/plain");
			PrintWriter out = response.getWriter();
			if (!accountsManager.isAccountExisted(connection, loginUserID, loginUserPassword)) 
				out.println(ResponseTags.LOGIN_ERROR_TAG);
			else {
				if (!accountsManager.isThisDeviceHasAccount(connection, loginUserID, loginUserPassword, loginDeviceID)) {
					String email = accountsManager.getEmailAssociateWithAccount(connection, loginUserID, loginUserPassword);
					Account newAccount = new Account(loginUserID, loginDeviceID, loginUserPassword, email);
					accountsManager.addNewAccount(connection, newAccount);
				}
				out.println(ResponseTags.LOGIN_SUCCESS_TAG);
			}
		}
	}
	
	/**
	 * Check for login in request. If the server notice a request to login in from client.
	 * It will first check if it is a valid account. If it is not, then throw an error message
	 * back to the client. If it is a valid account, it will check whether this device has already
	 * register under this account. If not, then create new account to include this device
	 * @param inherited request from the doPost
	 * @param inherited response from doPost
	 */
	private void checkForRegisterRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String registerUserID = (String) request.getParameter(RequestParameters.REGISTER_ACCOUNT_ID);
		String registerUserPassword = (String) request.getParameter(RequestParameters.REGISTER_ACCOUNT_PASSWORD);
		String registerUserEmail = (String) request.getParameter(RequestParameters.REGISTER_ACCOUNT_EMAIL);
		String registerDeviceID = (String) request.getParameter(RequestParameters.REGISTER_DEVICE_ID);
		if (registerUserID != null && registerUserPassword != null && registerDeviceID != null && registerUserEmail != null) {
			AccountsManager accountManager = new AccountsManager();
			response.setContentType("text/plain");
			PrintWriter out = response.getWriter();
			if (accountManager.isAccountWithGivenUsernameExisted(connection, registerUserID)) 
				out.println(ResponseTags.REGISTER_ERROR_TAG);
			else {
				Account newAccount = new Account(registerUserID, registerDeviceID, registerUserPassword, registerUserEmail);
				accountManager.addNewAccount(connection, newAccount);
				out.println(ResponseTags.REGISTER_SUCCESS_TAG);
			}
		}
	}
	
	/**
	 * Check for the coordinate input request. Upon a valid request, input the coordinate to our designated DB table
	 * @param inherited request from the doPost
	 */
	private void checkForGpsCoordinateInputRequest(HttpServletRequest request) throws ServletException, IOException {
		String coordinateUserID = (String) request.getParameter(RequestParameters.COORDINATE_INPUT_ACCOUNT_ID);
		String coordinateDeviceID = (String) request.getParameter(RequestParameters.COORDINATE_INPUT_DEVICE_ID);
		String coordinateTime = (String) request.getParameter(RequestParameters.COORDINATE_INPUT_TIME);
		String coordinateLongitude = (String) request.getParameter(RequestParameters.COORDINATE_INPUT_LONGITUDE);
		String coordinateLatitude = (String) request.getParameter(RequestParameters.COORDINATE_INPUT_LATITUDE);
		if (coordinateUserID != null && coordinateDeviceID != null && coordinateTime != null &&
				coordinateLongitude != null && coordinateLatitude != null) {
			GpsCoordinate coor = new GpsCoordinate(coordinateUserID, coordinateDeviceID, coordinateTime, coordinateLongitude, coordinateLatitude); //new GpsCoordinate(coordinate);
			gpsCoordinateManager.addNewGpsCoordinate(connection, coor);
		}
	}
	
	/**
	 * Check for the pin location input request. Upon a valid request, input the pin to designated DB table
	 * @param inherited request from the doPost
	 */
	private void checkForPinLocationInputRequest(HttpServletRequest request) {
		String pinUserID = (String) request.getParameter(RequestParameters.PIN_INPUT_ACCOUNT_ID);
		String pinTime = (String) request.getParameter(RequestParameters.PIN_INPUT_TIME);
		String pinLongitude = (String) request.getParameter(RequestParameters.PIN_INPUT_LONGITUDE);
		String pinLatitude = (String) request.getParameter(RequestParameters.PIN_INPUT_LATITUDE);
		String pinTitle = (String) request.getParameter(RequestParameters.PIN_INPUT_TITLE);
		String pinDescription = (String) request.getParameter(RequestParameters.PIN_INPUT_DESCRIPTION);
		if (pinUserID != null && pinTime != null && pinLongitude != null && pinLatitude != null
				&& pinTitle != null && pinDescription != null) {
			PinLocation pinLocation = new PinLocation(pinUserID, pinTime, pinLongitude, pinLatitude, pinTitle, pinDescription);
			pinLocationManager.addNewPin(connection, pinLocation);
		}
			
	}
	
	/**
	 * Check for coordinate output request. Response with the set of coordinate that fit the request criteria
	 * @param inherited request from the doPost
	 * @param inherited response from doPost
	 */
	private void checkForGpsCoordinateOutputRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		
	}
	
	/**
	 * Check for pin output request. Response with the set of pin that fit the request criteria
	 * @param inherited request from the doPost
	 * @param inherited response from doPost
	 */
	private void checkForPinLocationOutputRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pinRequestUserID = (String) request.getParameter(RequestParameters.PIN_REQUEST_ACCOUNT_ID);
		String pinRequestLowerLongitude = (String) request.getParameter(RequestParameters.PIN_REQUEST_LOWER_LONGITUDE);
		String pinRequestHigherLongitude = (String) request.getParameter(RequestParameters.PIN_REQUEST_HIGHER_LONGITUDE);
		String pinRequestLowerLatitude = (String) request.getParameter(RequestParameters.PIN_REQUEST_LOWER_LATITUDE);
		String pinRequestHigherLatitude = (String) request.getParameter(RequestParameters.PIN_REQUEST_HIGHER_LATITUDE);
		String pinRequestLowerTime = (String) request.getParameter(RequestParameters.PIN_REQUEST_LOWER_TIME);
		String pinRequestHigherTime = (String) request.getParameter(RequestParameters.PIN_REQUEST_HIGHER_TIME);
		if (pinRequestUserID != null && pinRequestLowerLongitude != null && pinRequestHigherLongitude != null &&
			pinRequestLowerLatitude != null && pinRequestHigherLatitude != null && pinRequestLowerTime != null &&
			pinRequestHigherTime != null) {
			PinConfig pinConfig = new PinConfig(pinRequestUserID, pinRequestLowerLongitude, pinRequestHigherLongitude,
					pinRequestLowerLatitude, pinRequestHigherLatitude, pinRequestLowerTime, pinRequestHigherTime);
			ArrayList<PinLocation> pinLocations = pinLocationManager.queryPins(connection, pinConfig);
			if (pinLocations == null) return;
			
			response.setContentType("text/plain");
			PrintWriter out = response.getWriter();
			//parse the pin and send back to the client with correct format
			for (PinLocation pinLocation : pinLocations) {
				out.println(getPinLocationString(pinLocation));
			}
		}
		
	}
	
    /**
     * Get the complete pin info in one string
     * @param PinLocation object
     */
    public String getPinLocationString(PinLocation pinLocation) {
   	 return pinLocation.getAccountID() + SpecialCharacters.delimiter +
   			 pinLocation.getTitle() + SpecialCharacters.delimiter +
   			 pinLocation.getDescription() + SpecialCharacters.delimiter +
   			 pinLocation.getTime() + SpecialCharacters.delimiter +
   			 pinLocation.getLongitude() + SpecialCharacters.delimiter +
   			 pinLocation.getLatitude() + SpecialCharacters.delimiter;
    }
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		System.out.println("request obtained");
		
		checkForLoginRequest(request, response);
		checkForRegisterRequest(request, response);
		
		checkForGpsCoordinateInputRequest(request);
		checkForGpsCoordinateOutputRequest(request, response);
		
		checkForPinLocationInputRequest(request);
		checkForPinLocationOutputRequest(request, response);
	
	}

}
