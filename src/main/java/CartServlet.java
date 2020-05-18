import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

public class CartServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CartResponse cartResponse = new CartResponse();
        String customerID = request.getParameter("cID");
        String productID = request.getParameter("pID");
        String quantity = request.getParameter("quantity");

        // TODO: going to need to map for different requests (add, remove, getAll, etc.); similar to Customer servlet

        if (customerID.isEmpty() || productID.isEmpty() || quantity.isEmpty()) {
            cartResponse.setMessage("cID, pID, and/or quantity are not specified.");
        } else {
            Connection conn = null;
            PreparedStatement stmt = null;

            try {
                conn = Database.dbConnect();
                String sqlSelect = "SELECT quantity FROM customer_cart WHERE customer_id=? AND product_id=?";
                stmt = conn.prepareStatement(sqlSelect);
                stmt.setInt(1, Integer.parseInt(customerID));
                stmt.setInt(2, Integer.parseInt(productID));
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    // Product is already in cart, so update quantity in database
                    int currentQuantity = rs.getInt("quantity");
                    int newQuantity = currentQuantity + Integer.parseInt(quantity); // TODO: assumes ADDING to cart; may need removing in future
                    String sqlUpdate = "UPDATE customer_cart SET quantity=? WHERE customer_id=? AND product_id=?";
                    stmt = conn.prepareStatement(sqlUpdate);
                    stmt.setInt(1, newQuantity);
                    stmt.setInt(2, Integer.parseInt(customerID));
                    stmt.setInt(3, Integer.parseInt(productID));
                    int rowsAffected = stmt.executeUpdate();

                    if (rowsAffected > 0) {
                        cartResponse.setMessage("OK");
                    } else {
                        cartResponse.setMessage(String.format("Error updating record in customer_cart where customer_id=%d, product_id=%d",
                                Integer.parseInt(customerID), Integer.parseInt(productID)));
                    }
                } else {
                    // Product not in cart, so add to customer_cart in database
                    String sqlInsert = "INSERT INTO customer_cart (customer_id, product_id, quantity) VALUES (?, ?, ?)";
                    stmt = conn.prepareStatement(sqlInsert);
                    stmt.setInt(1, Integer.parseInt(customerID));
                    stmt.setInt(2, Integer.parseInt(productID));
                    stmt.setInt(3, Integer.parseInt(quantity));
                    int rowsAffected = stmt.executeUpdate();

                    if (rowsAffected > 0) {
                        cartResponse.setMessage("OK");
                    } else {
                        cartResponse.setMessage(String.format("Error inserting record in customer_cart where customer_id=%d, product_id=%d",
                                Integer.parseInt(customerID), Integer.parseInt(productID)));
                    }
                }
            } catch (ClassNotFoundException | SQLException e) {
                cartResponse.setMessage(e.getMessage());
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
        }

        Gson gson = new Gson();
        String json = gson.toJson(cartResponse);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);

    }
}
