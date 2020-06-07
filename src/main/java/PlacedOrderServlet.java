import com.google.gson.Gson;
import org.glassfish.jersey.client.ClientConfig;

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


public class PlacedOrderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        processRequest(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(true);
        int cID = (int) session.getAttribute("customerID");
        OrderSummary cartSummary = new OrderSummary(cID);

        ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);
        WebTarget target = client.target(APIConfig.getBaseURI());

        String jsonCustomerResponse = target.path("v1").path("api").path("customers").path(String.valueOf(cID))
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .get(String.class);

        String jsonCardResponse = target.path("v1").path("api").path("creditcards").path(String.valueOf(cID))
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .get(String.class);

        Gson gson = new Gson();
        Customer customer = gson.fromJson(jsonCustomerResponse, Customer.class);
        CreditCard card = gson.fromJson(jsonCardResponse, CreditCard.class);

        cartSummary.setFirstName(customer.getFirstName());
        cartSummary.setLastName(customer.getLastName());
        cartSummary.setPhone(customer.getPhone());
        cartSummary.setEmail(customer.getEmail());
        cartSummary.setAddress(String.format("%s %s, %s %s",
                customer.getStreetAddress(), customer.getCity(), customer.getState(), customer.getZipcode()));
        cartSummary.setCountry(customer.getCountry());
        cartSummary.setCardNumber(card.getCardNumber());
        cartSummary.setShippingMethod(customer.getShipping());

        String json = gson.toJson(cartSummary);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }

}
