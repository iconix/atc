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
import db.*;
/**
 * Servlet implementation class ServerServlet
 */
@WebServlet("/ServerServlet")
public class ServerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection connection;
	
	@Override
	public void init() throws ServletException {
		connection = DBConnection.getConnection();
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
		String loginUserID = (String) request.getParameter("loginUserID");
		String loginUserPassword = (String) request.getParameter("loginUserPassword");
		String loginDeviceID = (String) request.getParameter("loginDeviceID");
		if (loginUserID != null && loginUserPassword != null && loginDeviceID != null) {
			AccountsManager accountManager = new AccountsManager();
			response.setContentType("text/plain");
			PrintWriter out = response.getWriter();
			if (!accountManager.isAccountExisted(connection, loginUserID, loginUserPassword)) 
				out.println("Error");
			else {
				if (!accountManager.isThisDeviceHasAccount(connection, loginUserID, loginUserPassword, loginDeviceID)) {
					String email = accountManager.getEmailAssociateWithAccount(connection, loginUserID, loginUserPassword);
					Account newAccount = new Account(loginUserID, loginDeviceID, loginUserPassword, email);
					accountManager.addNewAccount(connection, newAccount);
				}
				out.println("Logged in");
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
		String registerUserID = (String) request.getParameter("registerUserID");
		String registerUserPassword = (String) request.getParameter("registerUserPassword");
		String registerUserEmail = (String) request.getParameter("registerUserEmail");
		String registerDeviceID = (String) request.getParameter("registerDeviceID");
		if (registerUserID != null && registerUserPassword != null && registerDeviceID != null && registerUserEmail != null) {
			AccountsManager accountManager = new AccountsManager();
			response.setContentType("text/plain");
			PrintWriter out = response.getWriter();
			if (!accountManager.isAccountWithGivenUsernameExisted(connection, registerUserID)) 
				out.println("Error");
			else {
				Account newAccount = new Account(registerUserID, registerDeviceID, registerUserPassword, registerUserEmail);
				accountManager.addNewAccount(connection, newAccount);
				out.println("Registered");
			}
		}
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		checkForLoginRequest(request, response);
		checkForRegisterRequest(request, response);
		
		GpsCoordinateManager coorManager = new GpsCoordinateManager();
		PinLocationManager pinLocationManager = new PinLocationManager();
		
		
		String coordinate = (String)request.getParameter("coordinate");
		if (coordinate != null) {
			System.out.println(coordinate);
			GpsCoordinate coor = new GpsCoordinate(coordinate);
			coorManager.addNewGpsCoordinate(connection, coor);
		}
		
		String pinLocation = (String)request.getParameter("pinLocation");
		if (pinLocation != null) {
			System.out.println(pinLocation);
			PinLocation location = new PinLocation(pinLocation);
			pinLocationManager.addNewPin(connection, location);
		}
		/*
		String getCoordinate = (String)request.getParameter("getCoordinate");
		if (getCoordinate != null) {
			System.out.println(getCoordinate);
			
			ArrayList<GpsCoordinate> coordinates  = coorManager.getGpsCoordinateFromUser(getCoordinate);
			
			StringBuffer coordinateString = new StringBuffer();
			for (GpsCoordinate coor : coordinates) {
				coordinateString.append(coor.toString() + "\n");
			}
			response.setContentType("text/plain");
			PrintWriter out = response.getWriter();
			out.println(coordinateString.toString());
		}*/
	
	}

}
