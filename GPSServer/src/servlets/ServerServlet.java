package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import classesAndManagers.*;

/**
 * Servlet implementation class ServerServlet
 */
@WebServlet("/ServerServlet")
public class ServerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
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
			if (!accountManager.isAccountExisted(loginUserID, loginUserPassword)) 
				out.println("Error");
			else {
				if (!accountManager.isThisDeviceHasAccount(loginUserID, loginUserPassword, loginDeviceID)) {
					String email = accountManager.getEmailAssociateWithAccount(loginUserID, loginUserPassword);
					Account newAccount = new Account(loginUserID, loginDeviceID, loginUserPassword, email);
					accountManager.addNewAccount(newAccount);
				}
				out.println("Logged in");
			}
		}
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		checkForLoginRequest(request, response);
		
		
		GpsCoordinateManager coorManager = new GpsCoordinateManager();
		
		String coordinate = (String)request.getParameter("coordinate");
		if (coordinate != null) {
			System.out.println(coordinate);
			GpsCoordinate coor = new GpsCoordinate(coordinate);
			coorManager.addNewGpsCoordinate(coor);
		}
		
		
		String getSomething = (String)request.getParameter("getSomething");
		if (getSomething != null) {
			System.out.println(getSomething);
			response.setContentType("text/plain");
			PrintWriter out = response.getWriter();
			out.println("you get something");
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
