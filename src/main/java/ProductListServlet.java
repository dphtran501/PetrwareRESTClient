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
            } else {
                processGetAllProductsRequest(request, response);
            }
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
        lastViewedList.add(Integer.valueOf(productID));
        session.setAttribute("lastViewedList", lastViewedList);
        // TODO: Get it to redirect to product.html
    }

    private void processGetAllProductsRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ProductListResponse listResponse = new ProductListResponse();
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = Database.dbConnect();
            stmt = conn.createStatement();
            ResultSet rsProductCPU = stmt.executeQuery("SELECT * FROM product JOIN product_cpu ON product.id=product_cpu.product_id");
            ResultSet rsProductRAM = stmt.executeQuery("SELECT * FROM product JOIN product_ram ON product.id=product_ram.product_id");
            ResultSet rsProductVC = stmt.executeQuery("SELECT * FROM product JOIN product_video_card ON product.id=product_video_card.product_id");

            while (rsProductCPU.next()) {
                listResponse.addProductCPU(createProductCPU(rsProductCPU));
            }
            while (rsProductRAM.next()) {
                listResponse.addProductRAM(createProductRAM(rsProductRAM));
            }
            while (rsProductVC.next()) {
                listResponse.addProductVC(createProductVC(rsProductVC));
            }

            listResponse.setMessage("OK");

        } catch (SQLException | ClassNotFoundException e) {
            listResponse.setMessage(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        Gson gson = new Gson();
        String json = gson.toJson(listResponse);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
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
