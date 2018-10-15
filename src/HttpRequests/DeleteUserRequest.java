package HttpRequests;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class DeleteUserRequest {
	private final String GET_DELETE_REQUEST_URL = "http://last-minute-screws.000webhostapp.com/DeleteUser.php";
    private List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);

    public DeleteUserRequest(String id) throws Exception {
    	nameValuePairs.add(new BasicNameValuePair("user_id", id));
    }

    public List<NameValuePair> getValuePairs() {
        return this.nameValuePairs;
    }

    public String getDeleteRequestUrl() {
        return GET_DELETE_REQUEST_URL;
    }
}
