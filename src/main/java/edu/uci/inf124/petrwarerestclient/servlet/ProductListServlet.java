package edu.uci.inf124.petrwarerestclient.servlet;

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
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductListServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo(), methodIdentifier = null;
        if (pathInfo != null) {
            String[] pathParts = pathInfo.split("/");
            methodIdentifier = pathParts[1];
        }

        if (methodIdentifier == null) {
            // Future method involving no request parameters can be used here
        } else if (methodIdentifier.equals("get")) {
            if (request.getParameter("pID") != null) {
                processGetProductRequest(request, response);
            }
            else {
                processGetAllProductsRequest(request, response);
            }
        } else if (methodIdentifier.equals("searchBar")) {
            processSearchProductsRequest(request, response);
        }
    }

    private void processGetProductRequest(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(true);
        String productID = request.getParameter("pID");

        // Create list of recently viewed items if it doesn't exist
        if (session.getAttribute("lastViewedList") == null) {
            session.setAttribute("lastViewedList", new ArrayList<Integer>());
        }

        List<Integer> lastViewedList = (List<Integer>) session.getAttribute("lastViewedList");
        if (lastViewedList.contains(Integer.valueOf(productID))) {
            lastViewedList.remove(Integer.valueOf(productID));  // put it back in front if exists
        }
        lastViewedList.add(0, Integer.valueOf(productID));
        session.setAttribute("lastViewedList", lastViewedList);
        // TODO: Get it to redirect to product.html
    }

    private void processSearchProductsRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);
        WebTarget target = client.target(APIConfig.getBaseURI());

        String query = request.getParameter("search");
        String jsonResponse = target.path("v1").path("api").path("products").path("search").queryParam("query", query)
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .get(String.class);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // TODO: show last viewed in search results
//        // Include last viewed
//        HttpSession session = request.getSession(true);
//        if (session.getAttribute("lastViewedList") != null) {
//            List<Integer> lastViewedList = (List<Integer>) session.getAttribute("lastViewedList");
//            if (lastViewedList.size() > 0) {
//                response.getWriter().write("{");
//                response.getWriter().write("\"productList\":" + jsonResponse + ",");
//                response.getWriter().write("\"lastViewedList\":");
//
//                RequestDispatcher dispatcher = request.getRequestDispatcher("/LastViewedServlet/get");
//                dispatcher.include(request, response);
//
//                response.getWriter().write("}");
//            }
//        } else {
//            response.getWriter().write(jsonResponse);
//        }
        response.getWriter().write(jsonResponse);
    }

    private void processGetAllProductsRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);
        WebTarget target = client.target(APIConfig.getBaseURI());

        String jsonResponse = target.path("v1").path("api").path("products")
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .get(String.class);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Include last viewed
        HttpSession session = request.getSession(true);
        if (session.getAttribute("lastViewedList") != null) {
            List<Integer> lastViewedList = (List<Integer>) session.getAttribute("lastViewedList");
            if (lastViewedList.size() > 0) {
                response.getWriter().write("{");
                response.getWriter().write("\"productList\":" + jsonResponse + ",");
                response.getWriter().write("\"lastViewedList\":");

                RequestDispatcher dispatcher = request.getRequestDispatcher("/LastViewedServlet/get");
                dispatcher.include(request, response);

                response.getWriter().write("}");
            }
        } else {
            response.getWriter().write(jsonResponse);
        }
    }
}
