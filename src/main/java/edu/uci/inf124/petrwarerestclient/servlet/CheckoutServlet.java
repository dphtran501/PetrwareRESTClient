package edu.uci.inf124.petrwarerestclient.servlet;

import edu.uci.inf124.petrwarerestclient.model.CreditCard;
import edu.uci.inf124.petrwarerestclient.model.Customer;
import edu.uci.inf124.petrwarerestclient.resourcetarget.APIConfig;
import org.glassfish.jersey.client.ClientConfig;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;


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

         Customer customer = new Customer(cID);
         customer.setFirstName(request.getParameter("firstName"));
         customer.setLastName(request.getParameter("lastName"));
         customer.setPhone(request.getParameter("phone"));
         customer.setCountry(request.getParameter("country"));
         customer.setStreetAddress(request.getParameter("streetAddress"));
         customer.setCity(request.getParameter("city"));
         customer.setState(request.getParameter("state"));
         customer.setZipcode(request.getParameter("zipcode"));
         customer.setShipping(request.getParameter("shipping"));
         customer.setEmail(request.getParameter("email"));

         CreditCard card = new CreditCard();
         card.setId(cID);
         card.setCardNumber(request.getParameter("cardNumber"));
         card.setExpiration(request.getParameter("expiration"));
         card.setSecurityCode(request.getParameter("securityCode"));

         // Redirect Response to placedOrder.html
        // response.sendRedirect(request.getContextPath()+"/placedOrder.html");
        // Forward
        RequestDispatcher rd = request.getRequestDispatcher("placedOrder.html");
        rd.forward(request, response);

        //  Add/update database
         ClientConfig config = new ClientConfig();
         Client client = ClientBuilder.newClient(config);
         WebTarget target = client.target(APIConfig.getBaseURI());

         Response customerResponse = target.path("v1").path("api").path("customers")
                 .request()
                 .accept(MediaType.TEXT_PLAIN)
                 .put(Entity.entity(customer, MediaType.APPLICATION_JSON));

         if (customerResponse.getStatus() == 200) {
             Response cardResponse = target.path("v1").path("api").path("creditcards")
                     .request()
                     .accept(MediaType.TEXT_PLAIN)
                     .post(Entity.entity(card, MediaType.APPLICATION_JSON));
         }
    }
}
