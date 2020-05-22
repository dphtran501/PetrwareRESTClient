import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class LastViewedServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected  void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

    private void processGetLastViewedListRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductListResponse productListResponse = new ProductListResponse();
        HttpSession session = request.getSession(true);

        // Create list of recently viewed items if it doesn't exist
        if (session.getAttribute("lastViewedList") == null) {
            session.setAttribute("lastViewedList", new ArrayList<Integer>());
        }

        Connection conn = null;
        PreparedStatement stmtSelectCat = null;

        List<Integer> lastViewedList = (List<Integer>) session.getAttribute("lastViewedList");

        try {
            conn = Database.dbConnect();

            String sqlSelectCat = "SELECT category FROM product WHERE id=?";
            stmtSelectCat = conn.prepareStatement(sqlSelectCat);

            for (int i = 0; i < lastViewedList.size() && i < 5; i++) {
                int productID = lastViewedList.get(i);

                // Get category of product to use correct category table
                stmtSelectCat.setInt(1, productID);
                ResultSet rsCat = stmtSelectCat.executeQuery();
                String category = "";
                if (rsCat.next()) {
                    category = rsCat.getString("category");
                }

                String categoryTable = "";
                switch (category) {
                    case "cpu":
                        categoryTable = "product_cpu";
                        break;
                    case "ram":
                        categoryTable = "product_ram";
                        break;
                    case "videoCard":
                        categoryTable = "product_video_card";
                }

                // Get product
                String sql = String.format("SELECT * FROM product JOIN %s ON product.id=%s.product_id WHERE product.id=?",
                        categoryTable, categoryTable);
                try (PreparedStatement stmtSelectProduct = conn.prepareStatement(sql);) {
                    stmtSelectProduct.setInt(1, productID);
                    ResultSet rs = stmtSelectProduct.executeQuery();
                    if (rs.next()) {
                        String rsCategory = rs.getString("category");
                        switch (rsCategory) {
                            case "cpu":
                                productListResponse.addProductCPU(createProductCPU(rs));
                                break;
                            case "ram":
                                productListResponse.addProductRAM(createProductRAM(rs));
                                break;
                            case "videoCard":
                                productListResponse.addProductVC(createProductVC(rs));
                        }
                    }
                }

                productListResponse.setMessage("OK");

            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmtSelectCat != null) {
                    stmtSelectCat.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();;
            }
        }

        Gson gson = new Gson();
        String json = gson.toJson(productListResponse);

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
