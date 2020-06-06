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
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import java.sql.*;
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

    private void processGetProductRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

//    private void processSearchProductsRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//        ProductListResponse listResponse = new ProductListResponse();
//        Connection conn = null;
//        PreparedStatement stmt = null;
//        String key = "%" + request.getParameter("search") + "%";
//        try {
//            conn = Database.dbConnect();
//            stmt = conn.prepareStatement("SELECT * FROM product JOIN product_cpu ON product.id=product_cpu.product_id " +
//                    "WHERE product.brand LIKE ? " +
//                    "OR product.name LIKE ? " +
//                    "OR product.series LIKE ? " +
//                    "OR product.model LIKE ?");
//            stmt.setString(1, key);
//            stmt.setString(2, key);
//            stmt.setString(3, key);
//            stmt.setString(4, key);
//            ResultSet rsProductCPU = stmt.executeQuery();
//
//            stmt = conn.prepareStatement("SELECT * FROM product JOIN product_ram ON product.id=product_ram.product_id " +
//                    "WHERE product.brand LIKE ? " +
//                    "OR product.name LIKE ? " +
//                    "OR product.series LIKE ? " +
//                    "OR product.model LIKE ?");
//            stmt.setString(1, key);
//            stmt.setString(2, key);
//            stmt.setString(3, key);
//            stmt.setString(4, key);
//            ResultSet rsProductRAM = stmt.executeQuery();
//
//            stmt = conn.prepareStatement("SELECT * FROM product JOIN product_video_card ON product.id=product_video_card.product_id " +
//                    "WHERE product.brand LIKE ? " +
//                    "OR product.name LIKE ? " +
//                    "OR product.series LIKE ? " +
//                    "OR product.model LIKE ?");
//            stmt.setString(1, key);
//            stmt.setString(2, key);
//            stmt.setString(3, key);
//            stmt.setString(4, key);
//            ResultSet rsProductVC = stmt.executeQuery();
//
//            while (rsProductCPU.next()) {
//                listResponse.addProductCPU(createProductCPU(rsProductCPU));
//            }
//            while (rsProductRAM.next()) {
//                listResponse.addProductRAM(createProductRAM(rsProductRAM));
//            }
//            while (rsProductVC.next()) {
//                listResponse.addProductVC(createProductVC(rsProductVC));
//            }
//
//        } catch (SQLException | ClassNotFoundException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (stmt != null) {
//                    stmt.close();
//                }
//                if (conn != null) {
//                    conn.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//
//        Gson gson = new Gson();
//        String json = gson.toJson(listResponse);
//
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//
//        // Include last viewed list if exists
//        HttpSession session = request.getSession(true);
//        if (session.getAttribute("lastViewedList") != null) {
//            List<Integer> lastViewedList = (List<Integer>) session.getAttribute("lastViewedList");
//            if (lastViewedList.size() > 0) {
//                response.getWriter().write("{");
//                response.getWriter().write("\"productListResponse\":" + json + ",");
//                response.getWriter().write("\"lastViewedListResponse\":");
//
//                RequestDispatcher dispatcher = request.getRequestDispatcher("/LastViewedServlet/get");
//                dispatcher.include(request, response);
//
//                response.getWriter().write("}");
//            }
//        } else {
//            response.getWriter().write(json);
//        }
//    }

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

    private ProductCPU createProductCPU(ResultSet rs) throws SQLException {
        ProductCPU productCPU = new ProductCPU(createProduct(rs));
        productCPU.setProcessorsType(rs.getString("processorsType"));
        productCPU.setSocketType(rs.getString("socketType"));
        productCPU.setCoreName(rs.getString("coreName"));
        productCPU.setNumOfCores(rs.getInt("numOfCores"));
        productCPU.setNumOfThreads(rs.getInt("numOfThreads"));
        productCPU.setOperatingFrequency(rs.getDouble("operatingFrequency"));
        productCPU.setMaxTurboFrequency(rs.getDouble("maxTurboFrequency"));
        return productCPU;
    }

    private ProductRAM createProductRAM(ResultSet rs) throws SQLException {
        ProductRAM productRAM = new ProductRAM(createProduct(rs));
        productRAM.setCapacity(rs.getString("capacity"));
        productRAM.setSpeed(rs.getString("speed"));
        productRAM.setLatency(rs.getInt("latency"));
        productRAM.setTiming(rs.getString("timing"));
        productRAM.setColor(rs.getString("color"));
        productRAM.setColorLED(rs.getString("colorLED"));
        return productRAM;
    }

    private ProductVC createProductVC(ResultSet rs) throws SQLException {
        ProductVC productVC = new ProductVC(createProduct(rs));
        productVC.setInterfaceVC(rs.getString("interface"));
        productVC.setChipset(rs.getString("chipset"));
        productVC.setGpu(rs.getString("gpu"));
        productVC.setMemorySize(rs.getInt("memorySize"));
        productVC.setMemoryType(rs.getString("memoryType"));
        productVC.setMaxResolution(rs.getString("maxResolution"));
        productVC.setCooler(rs.getString("cooler"));
        productVC.setMaxGPULength(rs.getInt("maxGPULength"));
        productVC.setCardDimensions(rs.getString("cardDimensions"));
        return productVC;
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
