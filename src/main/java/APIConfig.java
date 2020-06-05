import javax.ws.rs.core.UriBuilder;
import java.net.URI;

public class APIConfig {

    private static final String SERVER_URL = "http://localhost:8081/PetrwareRESTService_war_exploded";

    public static URI getBaseURI() {

        return UriBuilder.fromUri(SERVER_URL).build();
    }
}
