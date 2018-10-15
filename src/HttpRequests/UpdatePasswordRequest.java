package HttpRequests;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class UpdatePasswordRequest {
	
	private final String GET_UPDATE_REQUEST_URL = "http://last-minute-screws.000webhostapp.com/UpdatePassword.php";
    private List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);

    public UpdatePasswordRequest(String username, String password) throws Exception {
    	nameValuePairs.add(new BasicNameValuePair("username", username));
        nameValuePairs.add(new BasicNameValuePair("password", password));
    }

    public List<NameValuePair> getValuePairs() {
        return this.nameValuePairs;
    }

    public String getUpdateRequestUrl() {
        return GET_UPDATE_REQUEST_URL;
    }

}
