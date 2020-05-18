import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

public class ProductServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductResponse productResponse = new ProductResponse();
        String productID = request.getParameter("id");
        String category = request.getParameter("category");

        if (productID.isEmpty() || category.isEmpty()) {
            productResponse.setMessage("Product ID and/or category are not specified.");
        } else {
            Connection conn = null;
            PreparedStatement stmt = null;
            try {
                conn = Database.dbConnect();
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

                if (categoryTable.isEmpty()) {
                    productResponse.setMessage(String.format("No category table for category %s", category));
                } else {
                    String sql = String.format("SELECT * FROM product JOIN %s ON product.id=%s.product_id WHERE product.id=?",
                            categoryTable, categoryTable);
                    stmt = conn.prepareStatement(sql);
                    stmt.setInt(1, Integer.parseInt(productID));
                    ResultSet rs = stmt.executeQuery();

                    if (rs.next()) {
                        String rsCategory = rs.getString("category");
                        switch (rsCategory) {
                            case "cpu":
                                productResponse.setProductCPU(createProductCPU(rs));
                                break;
                            case "ram":
                                productResponse.setProductRAM(createProductRAM(rs));
                                break;
                            case "videoCard":
                                productResponse.setProductVC(createProductVC(rs));
                        }
                    }

                    productResponse.setMessage("OK");

                }
            } catch (ClassNotFoundException | SQLException e) {
                productResponse.setMessage(e.getMessage());
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
        }

        Gson gson = new Gson();
        String json = gson.toJson(productResponse);

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
