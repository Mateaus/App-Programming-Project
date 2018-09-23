package HttpRequests;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;

public class LoginRequest {

    private static final String LOGIN_REQUEST_URL = "http://last-minute-screws.000webhostapp.com/Login.php";
    private List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);

    public LoginRequest(String username, String password) throws Exception{
        nameValuePairs.add(new BasicNameValuePair("username", username));
        nameValuePairs.add(new BasicNameValuePair("password", password));
    }

    public List<NameValuePair> getValuePairs() {
        return this.nameValuePairs;
    }

    public String getLoginRequestUrl() {
        return LOGIN_REQUEST_URL;
    }

    public Boolean checkRequest(String jsonString) throws Exception{
        JSONObject json = new JSONObject(jsonString);
        String response = json.get("success").toString();
        //String name = json.get("name").toString();
        //System.out.println(json);
        if(response.equals("true")) {
            return true;
        } else {
            return false;
        }
    }
}
