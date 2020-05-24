import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class PlacedOrderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(true);
        int cID = (int) session.getAttribute("customerID");
        OrderSummary cartSummary = new OrderSummary(cID);
        
         // Implement SQL Execute Query 
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = Database.dbConnect();
            stmt = conn.prepareStatement("SELECT * FROM customers JOIN creditcards ON customers.id=creditcards.id WHERE customers.id=?");
            stmt.setInt(1, cID);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()){
                cartSummary.setFirstName(rs.getString("firstName"));
                cartSummary.setLastName(rs.getString("lastName"));
                cartSummary.setPhone(rs.getString("phone"));
                cartSummary.setEmail(rs.getString("email"));
                cartSummary.setAddress(rs.getString("streetAddress") + " " + rs.getString("city") + ", " + rs.getString("state") + " " + rs.getString("zipcode"));
                cartSummary.setCountry(rs.getString("country"));
                cartSummary.setCardNumber(rs.getString("cardNumber"));
                cartSummary.setShippingMethod(rs.getString("shipping"));
            }
            
        } catch (ClassNotFoundException | SQLException e) {
            // TODO: throw exception up to processRequest to add its message to response object?
            // maybe Ajax already has a way of knowing if there was an exception here
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        Gson gson = new Gson();
        String json = gson.toJson(cartSummary);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }

}
