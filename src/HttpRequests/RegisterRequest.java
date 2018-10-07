package HttpRequests;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class RegisterRequest {

    private static final String REGISTER_REQUEST_URL = "http://last-minute-screws.000webhostapp.com/Register.php";
    private List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);

    public RegisterRequest(String name, String username, String password) {
        nameValuePairs.add(new BasicNameValuePair("name", name));
        nameValuePairs.add(new BasicNameValuePair("username", username));
        nameValuePairs.add(new BasicNameValuePair("password", password));
    }

    public String getRegisterRequestUrl() {
        return REGISTER_REQUEST_URL;
    }

    public List<NameValuePair> getValuePairs() {
        return this.nameValuePairs;
    }

}
