package HttpRequests;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ActivityRequest {

    private static final String ACTIVITY_REQUEST_URL = "http://last-minute-screws.000webhostapp.com/Activity.php";
    private List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);

    public ActivityRequest(String studentId, String studentStatus) {
        nameValuePairs.add(new BasicNameValuePair("user_id", studentId));
        nameValuePairs.add(new BasicNameValuePair("status", studentStatus));
    }

    public String getActivityRequestUrl() {
        return ACTIVITY_REQUEST_URL;
    }

    public List<NameValuePair> getValuePairs() {
        return this.nameValuePairs;
    }

    public void checkRequest(String jsonString) throws Exception {
        JSONObject json = new JSONObject(jsonString);
        String response = json.get("success").toString();
        if(response.equals("true")){
            System.out.println("Request was successful.");
        } else {
            System.out.println("Request wasn't successful.");
        }
    }
}
