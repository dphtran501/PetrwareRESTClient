import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.annotation.Resource;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "ZipcodeServlet", urlPatterns = "/api/zipcode")
public class ZipcodeServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        String zipcode = request.getParameter("zipcode");

        PrintWriter out = response.getWriter();

        try {
            String query = "SELECT z.state as state, city, combinedRate FROM zip_codes z JOIN tax_rates tr ON zip=zipcode WHERE zip=?";
            Connection dbcon = Database.dbConnect();
            PreparedStatement pstatement = dbcon.prepareStatement(query);
            pstatement.setString(1, zipcode);
            ResultSet rs = pstatement.executeQuery();

            JsonArray jsonArray = new JsonArray();
            JsonObject jsonObject = new JsonObject();
            while(rs.next()) {
                jsonObject.addProperty("city", rs.getString("city"));
                jsonObject.addProperty("state", rs.getString("state"));
                jsonObject.addProperty("combinedRate", rs.getDouble("combinedRate"));

                jsonArray.add(jsonObject);
            }
            pstatement.close();
            rs.close();

            out.write(jsonArray.toString());
            response.setStatus(200);

            out.close();
            dbcon.close();

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }
}
