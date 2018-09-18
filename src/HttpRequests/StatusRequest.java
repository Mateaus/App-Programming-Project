package HttpRequests;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class StatusRequest {

    private static final String STATUS_REQUEST_URL = "http://last-minute-screws.000webhostapp.com/Status.php";
    private List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);

    public StatusRequest(String status) throws Exception {
        nameValuePairs.add(new BasicNameValuePair("status", status));
    }

    public List<NameValuePair> getValuePairs() {
        return this.nameValuePairs;
    }

    public String getStatusRequestUrl() {
        return STATUS_REQUEST_URL;
    }
}
