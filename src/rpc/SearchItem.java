package rpc;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import db.DBConnection;
import db.DBConnectionFactory;
import entity.Item;
import external.TicketMasterAPI;

/**
 * Servlet implementation class SearchItem
 */
@WebServlet("/search")  //localhost/Jupiter/search
public class SearchItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchItem() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// allow access only if session exists
		HttpSession session = request.getSession(false);
		if (session == null) {
			response.setStatus(403);
			return;
		}

        // optional
		String userId = session.getAttribute("user_id").toString(); 


		double lat = Double.parseDouble(request.getParameter("lat"));
		double lon = Double.parseDouble(request.getParameter("lon"));
		String term = request.getParameter("term");
	//	String userId = request.getParameter("user_id");
		
     	DBConnection connection = DBConnectionFactory.getDBConnection();
		List<Item> items = connection.searchItems(lat, lon, term);
		Set<String> favoriateItems = new HashSet<>();
		
//		TicketMasterAPI tmAPI = new TicketMasterAPI();
//		List<Item> items = tmAPI.search(lat, lon, term);
		List<JSONObject> list = new ArrayList<>();
		try {
			favoriateItems = connection.getFavoriteItemIds(userId);
			for (Item item : items) {
				// Add a thin version of item object
				JSONObject obj = item.toJSONObject();
				// If this item had been marked favorite, light the favorite icon
				obj.put("favorite", favoriateItems.contains(item.getItemId()));
				list.add(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONArray array = new JSONArray(list);
		RpcHelper.writeJsonArray(response, array);
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
