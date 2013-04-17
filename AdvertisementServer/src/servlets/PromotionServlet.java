package servlets;

import classesAndManagers.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.DBConnection;

/**
 * Servlet implementation class PromotionServlet
 */
@WebServlet("/PromotionServlet")
public class PromotionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    Connection connection;
    PromotionsManager promotionManager;
    
	@Override
	public void init() throws ServletException {
		connection = DBConnection.getConnection();
		promotionManager = new PromotionsManager();	
	}
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PromotionServlet() {
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
	
	private void checkForDealOutputRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String dealRequest = (String) request.getParameter("deal");
		if (dealRequest != null) {
			ArrayList<Promotion> promotions = promotionManager.queryPromotion(connection);
			if (promotions == null) return;
			
			response.setContentType("text/plain");
			PrintWriter out = response.getWriter();
			for (Promotion promotion : promotions)
				out.println(promotion.getPromotionString());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("just checking");
		checkForDealOutputRequest(request, response);
		
		/*ArrayList<Promotion> promotionList = promotionManager.queryPromotion(connection);
		if (promotionList == null) return;
		
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		for (Promotion promotion : promotionList)
			out.println(promotion.getTitle());*/
	}

}
