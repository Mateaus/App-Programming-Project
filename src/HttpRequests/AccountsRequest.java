package HttpRequests;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class AccountsRequest {
	
	private final String ACCOUNT_REQUEST_URL = "http://last-minute-screws.000webhostapp.com/Accounts.php";
	private List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
	
	public AccountsRequest(String name, String username, String password) {
		nameValuePairs.add(new BasicNameValuePair("name", name));
		nameValuePairs.add(new BasicNameValuePair("username", username));
		nameValuePairs.add(new BasicNameValuePair("password", password));
	}

}
