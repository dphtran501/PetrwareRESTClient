package edu.uci.inf124.petrwarerestclient.servlet;

import edu.uci.inf124.petrwarerestclient.resourcetarget.APIConfig;
import org.glassfish.jersey.client.ClientConfig;

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

public class ProductServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);
        WebTarget target = client.target(APIConfig.getBaseURI());

        HttpSession session = request.getSession(true);
        List<Integer> lastViewedList = (List<Integer>) session.getAttribute("lastViewedList");
        int productID = lastViewedList.get(0);  // TODO: should check if size is 0

        String jsonResponse = target.path("v1").path("api").path("products").path(String.valueOf(productID))
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .get(String.class);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse);
    }

}
