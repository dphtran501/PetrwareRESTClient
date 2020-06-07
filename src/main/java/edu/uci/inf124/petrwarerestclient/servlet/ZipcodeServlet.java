package edu.uci.inf124.petrwarerestclient.servlet;

import edu.uci.inf124.petrwarerestclient.resourcetarget.APIConfig;
import org.glassfish.jersey.client.ClientConfig;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@WebServlet(name = "ZipcodeServlet", urlPatterns = "/api/zipcode")
public class ZipcodeServlet extends HttpServlet {

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

        String zipcode = request.getParameter("zipcode");
        String jsonResponse = target.path("v1").path("api").path("checkout").path("zipcode").queryParam("zipcode", zipcode)
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .get(String.class);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse);
    }
}
