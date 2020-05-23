import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class CheckoutServlet extends HttpServlet {

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
        
        // Forward to placedOrder.html
        RequestDispatcher rd = request.getRequestDispatcher("placedOrder.html");
        rd.forward(request, response);
//        HttpSession session = request.getSession(true);
        
//        int cID = Integer.parseInt(request.getParameter("cID"));
        String cID = request.getParameter("cID");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String phone = request.getParameter("phone");
        String country = request.getParameter("country");
        String streetAddress = request.getParameter("streetAddress");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String zipcode = request.getParameter("zipcode");
        String shippingMethod = request.getParameter("shipping");
        String email = request.getParameter("email");
        String cardNumber = request.getParameter("cardNumber");
        String expiration = request.getParameter("expiration");
        String securityCode = request.getParameter("securityCode");
        
        
        // Setting data in Order Summary Object 
        OrderSummary cartSummary = new OrderSummary(cID);
        
        cartSummary.setFirstName(firstName);
        cartSummary.setLastName(lastName);
        cartSummary.setPhone(phone);
        cartSummary.setEmail(email);
        cartSummary.setAddress(streetAddress + " " + city + ", " + state + " " + zipcode);
        cartSummary.setCountry(country);
        cartSummary.setCardNumber(cardNumber);
        cartSummary.setShippingMethod(shippingMethod);
        
        Gson gson = new Gson();
        String json = gson.toJson(cartSummary);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
        
        // Implement SQL Update to Customer
//        Connection conn = null;
//        Statement stmt = null;
//        try {
//            conn = Database.dbConnect();
//            stmt = conn.createStatement();
//            int rowsAffected = stmt.executeUpdate("INSERT into customers (firstname, lastname) VALUES ('UNSAVED', 'CUSTOMER')");
//            if (rowsAffected > 0) {
//                ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");
//                if (rs.next()) {
//                    id = rs.getInt(1);
//                }
//            }
//
//        } catch (ClassNotFoundException | SQLException e) {
//            // TODO: throw exception up to processRequest to add its message to response object?
//            // maybe Ajax already has a way of knowing if there was an exception here
//            e.printStackTrace();
//        } finally {
//            try {
//                if (stmt != null) {
//                    stmt.close();
//                }
//                if (conn != null) {
//                    conn.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
        
    }

}
