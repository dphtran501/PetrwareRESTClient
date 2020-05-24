import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
        
        HttpSession session = request.getSession(true);
        int cID = (int) session.getAttribute("customerID");
        String customerID = Integer.toString(cID);
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
        
        
        
//        Redirect Response to placedOrder.html
        response.sendRedirect(request.getContextPath()+"/placedOrder.html");
        // Forward
//        RequestDispatcher rd = request.getRequestDispatcher("placedOrder.html");
//        rd.forward(request, response);
        
        // Implement SQL Update to Customer
        Connection conn = null;
        PreparedStatement st = null;
        PreparedStatement st2 = null;
        try {
            conn = Database.dbConnect();
            st = conn.prepareStatement("update customers set firstName=?, lastName=?, phone=?, country=?, streetAddress=?," +
                " city=?, state=?, zipcode=?, shipping=?, email=? where id=?");
            
           st.setString(1, firstName);
           st.setString(2, lastName);
           st.setString(3, phone);
           st.setString(4, country);
           st.setString(5, streetAddress);
           st.setString(6, city);
           st.setString(7, state);
           st.setString(8, zipcode);
           st.setString(9, shippingMethod);
           st.setString(10, email);
           st.setString(11, customerID);
           int rowAffectedByStatement = st.executeUpdate();
           
//           INSERT INTO creditcards VALUES(:cID, :cardNumber, :expiration, :securityCode)
           st2 = conn.prepareStatement("insert into creditcards values(?, ?, ?, ?)");
           st2.setString(1, customerID);
           st2.setString(2, cardNumber);
           st2.setString(3, expiration);
           st2.setString(4, securityCode);
           int rowsAffected = st2.executeUpdate();
           

        } catch (ClassNotFoundException | SQLException e) {
            // TODO: throw exception up to processRequest to add its message to response object?
            // maybe Ajax already has a way of knowing if there was an exception here
            e.printStackTrace();
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
                if (st2 != null) {
                    st2.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        // Setting data in Order Summary Object 
//        OrderSummary cartSummary = new OrderSummary(cID);
//        
//        cartSummary.setFirstName(firstName);
//        cartSummary.setLastName(lastName);
//        cartSummary.setPhone(phone);
//        cartSummary.setEmail(email);
//        cartSummary.setAddress(streetAddress + " " + city + ", " + state + " " + zipcode);
//        cartSummary.setCountry(country);
//        cartSummary.setCardNumber(cardNumber);
//        cartSummary.setShippingMethod(shippingMethod);
//        
//        Gson gson = new Gson();
//        String json = gson.toJson(cartSummary);
//
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//        response.getWriter().write(cID + firstName);
    }

}
