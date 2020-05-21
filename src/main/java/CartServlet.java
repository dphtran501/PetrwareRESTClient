import com.google.gson.Gson;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
        String pathInfo = request.getPathInfo(), methodIdentifier = null;
        if (pathInfo != null) {
            String [] pathParts = pathInfo.split("/");
            methodIdentifier = pathParts[1];
        }

        if (methodIdentifier == null) {
            // Future method involving no request parameters can be used here
        } else if (methodIdentifier.equals("add")) {
            processAddItemRequest(request, response);
        } else if (methodIdentifier.equals("get")) {
            processGetAllItemsRequest(request, response);
            // If pID received, reserve for getting specific item from cart
        }
    }

    private void processAddItemRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        CartResponse cartResponse = new CartResponse();
        HttpSession session = request.getSession(true);

        // Create customer record if no customerID found
        if (session.getAttribute("customerID") == null) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/CustomerServlet/new");
            dispatcher.include(request, response);
        }
        String productID = request.getParameter("pID");
        String quantity = request.getParameter("quantity");

        if (session.getAttribute("customerID") == null) {
            cartResponse.setMessage("Error retrieving customer ID");
        } else if (productID.isEmpty() || quantity.isEmpty()) {
            cartResponse.setMessage("pID, and/or quantity are not specified.");
        } else {
            Connection conn = null;
            PreparedStatement stmt = null;
            int customerID = (int) session.getAttribute("customerID");

            try {
                conn = Database.dbConnect();
                String sqlSelect = "SELECT quantity FROM customer_cart WHERE customer_id=? AND product_id=?";
                stmt = conn.prepareStatement(sqlSelect);
                stmt.setInt(1, customerID);
                stmt.setInt(2, Integer.parseInt(productID));
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    // Product is already in cart, so update quantity in database
                    int currentQuantity = rs.getInt("quantity");
                    int newQuantity = currentQuantity + Integer.parseInt(quantity);
                    String sqlUpdate = "UPDATE customer_cart SET quantity=? WHERE customer_id=? AND product_id=?";
                    stmt = conn.prepareStatement(sqlUpdate);
                    stmt.setInt(1, newQuantity);
                    stmt.setInt(2, customerID);
                    stmt.setInt(3, Integer.parseInt(productID));
                    int rowsAffected = stmt.executeUpdate();

                    if (rowsAffected > 0) {
                        cartResponse.setMessage("OK");
                    } else {
                        cartResponse.setMessage(String.format("Error updating record in customer_cart where customer_id=%d, product_id=%d",
                                customerID, Integer.parseInt(productID)));
                    }
                } else {
                    // Product not in cart, so add to customer_cart in database
                    String sqlInsert = "INSERT INTO customer_cart (customer_id, product_id, quantity) VALUES (?, ?, ?)";
                    stmt = conn.prepareStatement(sqlInsert);
                    stmt.setInt(1, customerID);
                    stmt.setInt(2, Integer.parseInt(productID));
                    stmt.setInt(3, Integer.parseInt(quantity));
                    int rowsAffected = stmt.executeUpdate();

                    if (rowsAffected > 0) {
                        cartResponse.setMessage("OK");
                    } else {
                        cartResponse.setMessage(String.format("Error inserting record in customer_cart where customer_id=%d, product_id=%d",
                                customerID, Integer.parseInt(productID)));
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

    private void processGetAllItemsRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CartResponse cartResponse = new CartResponse();
        HttpSession session = request.getSession(true);

        // Create customer record if no customerID found
        if (session.getAttribute("customerID") == null) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/CustomerServlet/new");
            dispatcher.include(request, response);
        }

        if (session.getAttribute("customerID") == null) {
            cartResponse.setMessage("Error retrieving customer ID");
        } else {
            Connection conn = null;
            PreparedStatement stmt = null;
            PreparedStatement stmtVC = null;
            int customerID = (int) session.getAttribute("customerID");

            try {
                conn = Database.dbConnect();
                String sql = "SELECT * FROM customer_cart JOIN product ON product_id=id WHERE customer_id=?";
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, customerID);
                ResultSet rs = stmt.executeQuery();

                Cart cart = new Cart(customerID);
                while (rs.next()) {
                    // Has to be a better way of including gpu field
                    if (rs.getString("category") != null && rs.getString("category").equals("videoCard")) {
                        String sqlVC = "SELECT customer_cart.*, product.*, product_video_card.gpu FROM customer_cart "
                                        + "JOIN product ON customer_cart.product_id=product.id "
                                        + "JOIN product_video_card ON product.id=product_video_card.product_id "
                                        + "WHERE customer_id=? AND product.id=?";
                        stmtVC = conn.prepareStatement(sqlVC);
                        stmtVC.setInt(1, customerID);
                        stmtVC.setInt(2, rs.getInt("product_id"));
                        ResultSet rsVC = stmtVC.executeQuery();
                        while (rsVC.next()) {
                            cart.addCartItem(createCartItemVC(rsVC));
                        }
                    } else {
                        cart.addCartItem(createCartItem(rs));
                    }
                }

                cartResponse.setCart(cart);
                cartResponse.setMessage("OK");

            } catch (ClassNotFoundException | SQLException e) {
                cartResponse.setMessage(e.getMessage());
                e.printStackTrace();
            } finally {
                try {
                    if (stmt != null) {
                        stmt.close();
                    }
                    if (stmtVC != null) {
                        stmtVC.close();
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

    private CartItem createCartItem(ResultSet rs) throws SQLException {
        CartItem cartItem = new CartItem();
        cartItem.setProductID(rs.getInt("product_id"));
        cartItem.setQuantity(rs.getInt("quantity"));
        cartItem.setProduct(createProduct(rs));

        return cartItem;
    }

    // Has to be a better way of including gpu field
    private CartItem createCartItemVC(ResultSet rs) throws SQLException {
        CartItem cartItem = new CartItem();
        cartItem.setProductID(rs.getInt("product_id"));
        cartItem.setQuantity(rs.getInt("quantity"));

        ProductVC videoCard = new ProductVC(createProduct(rs));
        videoCard.setGpu(rs.getString("gpu"));
        cartItem.setVideoCard(videoCard);

        return cartItem;
    }

    private Product createProduct(ResultSet rs) throws SQLException {
        Product product = new Product(rs.getInt("id"));
        product.setModel(rs.getString("model"));
        product.setBrand(rs.getString("brand"));
        product.setName(rs.getString("name"));
        product.setSeries(rs.getString("series"));
        product.setCategory(rs.getString("category"));
        product.setPrice(rs.getDouble("price"));
        product.setDescription(rs.getString("description"));
        product.setImgSrc(rs.getString("imgSrc"));
        return product;
    }
}
