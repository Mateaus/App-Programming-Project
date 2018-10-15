package HttpRequests;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class FindUserRequest {
	
	private final String GET_USER_REQUEST_URL = "http://last-minute-screws.000webhostapp.com/FindUser.php";
    private List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);

    public FindUserRequest(String username) throws Exception {
        nameValuePairs.add(new BasicNameValuePair("username", username));
    }

    public List<NameValuePair> getValuePairs() {
        return this.nameValuePairs;
    }

    public String getUserRequestUrl() {
        return GET_USER_REQUEST_URL;
    }

}
