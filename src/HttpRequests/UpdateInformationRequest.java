package HttpRequests;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class UpdateInformationRequest {
	
	private final String GET_UPDATE_REQUEST_URL = "http://last-minute-screws.000webhostapp.com/UpdateInformation.php";
    private List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);

    public UpdateInformationRequest(String id, String username, String password) throws Exception {
    	nameValuePairs.add(new BasicNameValuePair("user_id", id));
    	nameValuePairs.add(new BasicNameValuePair("name", username));
        nameValuePairs.add(new BasicNameValuePair("username", password));
    }

    public List<NameValuePair> getValuePairs() {
        return this.nameValuePairs;
    }

    public String getUpdateRequestUrl() {
        return GET_UPDATE_REQUEST_URL;
    }

}
