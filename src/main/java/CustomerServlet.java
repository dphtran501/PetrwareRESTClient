import com.google.gson.Gson;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        int id = -1;    // -1 means no customer record created

        Connection conn = null;
        Statement stmt = null;
        try {
            conn = Database.dbConnect();
            stmt = conn.createStatement();
            int rowsAffected = stmt.executeUpdate("INSERT into customers (firstname, lastname) VALUES ('UNSAVED', 'CUSTOMER')");
            if (rowsAffected > 0) {
                ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");
                if (rs.next()) {
                    id = rs.getInt(1);
                }
            }

        } catch (ClassNotFoundException | SQLException e) {
            // TODO: throw exception up to processRequest to add its message to response object?
            // maybe Ajax already has a way of knowing if there was an exception here
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

        return id;
    }
}
