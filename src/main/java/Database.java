import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    public static Connection dbConnect() throws ClassNotFoundException, SQLException {
        Class.forName("org.mariadb.jdbc.Driver");
        String dbURL = "jdbc:mariadb://" + Credentials.HOSTNAME + ":" + Credentials.PORT_NUMBER + "/" + Credentials.DATABASE;
        return DriverManager.getConnection(dbURL, Credentials.DB_USERNAME, Credentials.DB_PASSWORD);
    }
}
