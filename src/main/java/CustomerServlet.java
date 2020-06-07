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

public class CustomerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CustomerResponse customerResponse = new CustomerResponse();
        HttpSession session = request.getSession(true);

        String methodIdentifier = null;
        if (request.getAttribute(RequestDispatcher.INCLUDE_PATH_INFO) != null) {
            String pathInfo = (String) request.getAttribute(RequestDispatcher.INCLUDE_PATH_INFO);
            String[] pathParts = pathInfo.split("/");
            methodIdentifier = pathParts[1];
        }
        else if (request.getPathInfo() != null) {
            String[] pathParts = request.getPathInfo().split("/");
            methodIdentifier = pathParts[1];
        }

        switch (methodIdentifier) {
            case "new":
                if (session.getAttribute("customerID") == null) {
                    int customerID = createCustomer();
                    if (customerID == -1) {
                        customerResponse.setMessage("Error creating new customer");
                    } else {
                        session.setAttribute("customerID", customerID);
                        Customer customer = new Customer(customerID);
                        customerResponse.setCustomer(customer);
                        customerResponse.setMessage("OK");
                    }
                }
                if (request.getAttribute(RequestDispatcher.INCLUDE_PATH_INFO) != null) return; // Don't need to return response for this
                break;
        }

        Gson gson = new Gson();
        String json = gson.toJson(customerResponse);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }

    private int createCustomer() {
        ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);
        WebTarget target = client.target(APIConfig.getBaseURI());

        String jsonResponse = target.path("v1").path("api").path("customers")
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .post(null, String.class);

        Gson gson = new Gson();
        Customer newCustomer = gson.fromJson(jsonResponse, Customer.class);
        return newCustomer.getId();
    }
}
