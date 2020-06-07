import com.google.gson.Gson;
import org.glassfish.jersey.client.ClientConfig;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
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

    private void processGetAllItemsRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(true);

        Cart cart = (Cart) session.getAttribute("cart");
        if (cart != null) {
            ClientConfig config = new ClientConfig();
            Client client = ClientBuilder.newClient(config);
            WebTarget target = client.target(APIConfig.getBaseURI());

            List<CartItem> cartItems = cart.getCartItems();
            for (CartItem item : cartItems) {
                String jsonResponse = target.path("v1").path("api").path("products").path(String.valueOf(item.getProductID()))
                        .request()
                        .accept(MediaType.APPLICATION_JSON)
                        .get(String.class);

                Gson gson = new Gson();
                Product product = gson.fromJson(jsonResponse, Product.class);
                item.setProduct(product);
            }

            cart.setCartItems(cartItems); // cartItems update in foreach loop

            Gson gson = new Gson();
            String json = gson.toJson(cart);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        }
    }
}
