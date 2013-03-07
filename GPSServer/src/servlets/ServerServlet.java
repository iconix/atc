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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		GpsCoordinateManager coorManager = new GpsCoordinateManager();
		
		String coordinate = (String)request.getParameter("coordinate");
		if (coordinate != null) {
			System.out.println(coordinate);
			GpsCoordinate coor = new GpsCoordinate(coordinate);
			coorManager.addNewGpsCoordinate(coor);
		}
		
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
		}
	}

}
