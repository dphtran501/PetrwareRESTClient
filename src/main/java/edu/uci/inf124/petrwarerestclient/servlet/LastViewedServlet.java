package edu.uci.inf124.petrwarerestclient.servlet;

import com.google.gson.Gson;
import edu.uci.inf124.petrwarerestclient.model.ProductCPU;
import edu.uci.inf124.petrwarerestclient.model.ProductRAM;
import edu.uci.inf124.petrwarerestclient.model.ProductVC;
import edu.uci.inf124.petrwarerestclient.resourcetarget.APIConfig;
import edu.uci.inf124.petrwarerestclient.response.ProductListResponse;
import org.glassfish.jersey.client.ClientConfig;
import org.json.JSONObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LastViewedServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processRequest(request, response);
    }

    protected  void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo(), methodIdentifier = null;
        if (pathInfo != null) {
            String[] pathParts = pathInfo.split("/");
            methodIdentifier = pathParts[1];
        }

        if (methodIdentifier == null) {
            // Future method involving no request parameters can be used here
        } else if (methodIdentifier.equals("get")) {
            processGetLastViewedListRequest(request, response);
        }
    }

    private void processGetLastViewedListRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);
        WebTarget target = client.target(APIConfig.getBaseURI());

        // Create list of recently viewed items if it doesn't exist
        HttpSession session = request.getSession(true);
        if (session.getAttribute("lastViewedList") == null) {
            session.setAttribute("lastViewedList", new ArrayList<Integer>());
        }

        ProductListResponse listResponse = new ProductListResponse();
        List<Integer> lastViewedList = (List<Integer>) session.getAttribute("lastViewedList");
        // Get last 5 (or less) viewed products
        for (int i = 0; i < lastViewedList.size() && i < 5; i++) {
            int productID = lastViewedList.get(i);
            String jsonResponse = target.path("v1").path("api").path("products").path(String.valueOf(productID))
                    .request()
                    .accept(MediaType.APPLICATION_JSON)
                    .get(String.class);

            JSONObject jsonObject = new JSONObject(jsonResponse);
            String category = jsonObject.getString("category");
            Gson gson = new Gson();
            if (category.equals("cpu")) {
                ProductCPU cpu = gson.fromJson(jsonResponse, ProductCPU.class);
                listResponse.addProductCPU(cpu);
            } else if (category.equals("ram")) {
                ProductRAM ram = gson.fromJson(jsonResponse, ProductRAM.class);
                listResponse.addProductRAM(ram);
            } else if (category.equals("videoCard")) {
                ProductVC vc = gson.fromJson(jsonResponse, ProductVC.class);
                listResponse.addProductVC(vc);
            }
        }

        // Set json response
        Gson gson = new Gson();
        String json = gson.toJson(listResponse);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }

}
