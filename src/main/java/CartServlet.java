import com.google.gson.Gson;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
        List<Integer> lastViewedList = (List<Integer>) session.getAttribute("lastViewedList");
        String quantity = request.getParameter("quantity");

        if (session.getAttribute("customerID") == null) {
            cartResponse.setMessage("Error retrieving customer ID");
        } else if (session.getAttribute("lastViewedList") == null) {
            cartResponse.setMessage("Error getting product ID");
        } else if (quantity.isEmpty()) {
            cartResponse.setMessage("Quantity is not specified.");
        } else {
            int customerID = (int) session.getAttribute("customerID");
            int productID = lastViewedList.get(0);

            if (session.getAttribute("cart") == null) {
                session.setAttribute("cart", new Cart(customerID));
            }
            Cart cart = (Cart) session.getAttribute("cart");
            cart.addCartItem(new CartItem(productID, Integer.parseInt(quantity)));
            session.setAttribute("cart", cart);

            cartResponse.setMessage("OK");
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

        Cart cart = (Cart) session.getAttribute("cart");

        if (cart != null) {
            Connection conn = null;
            try {
                conn = Database.dbConnect();
                String sql = "SELECT * FROM product WHERE id=?";
                String sqlVC = "SELECT product.*, product_video_card.gpu FROM product "
                        + "JOIN product_video_card ON product.id=product_video_card.product_id "
                        + "WHERE product.id=?";

                List<CartItem> cartItems = cart.getCartItems();
                for (CartItem item : cartItems) {

                    try (PreparedStatement stmt = conn.prepareStatement(sql);
                         PreparedStatement stmtVC = conn.prepareStatement(sqlVC);) {

                        stmt.setInt(1, item.getProductID());
                        ResultSet rs = stmt.executeQuery();

                        if (rs.next()) {
                            // Has to be a better way of including gpu field
                            if (rs.getString("category") != null && rs.getString("category").equals("videoCard")) {

                                stmtVC.setInt(1, item.getProductID());
                                ResultSet rsVC = stmtVC.executeQuery();
                                if (rsVC.next()) {
                                    ProductVC videoCard = new ProductVC(createProduct(rsVC));
                                    videoCard.setGpu(rsVC.getString("gpu"));
                                    item.setVideoCard(videoCard);
                                }
                            } else {
                                item.setProduct(createProduct(rs));
                            }
                        }
                    }
                }

                cart.setCartItems(cartItems); // cartItems update in foreach loop
                cartResponse.setCart(cart);
                cartResponse.setMessage("OK");

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

        Gson gson = new Gson();
        String json = gson.toJson(cartResponse);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
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
