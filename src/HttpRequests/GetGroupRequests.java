package HttpRequests;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;

public class GetGroupRequests {
    private final String GET_REQUEST_URL = "http://last-minute-screws.000webhostapp.com/GetGroup.php";
    private List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);

    public GetGroupRequests() {
        
    }

    public String getGroupRequestUrl() {
        return GET_REQUEST_URL;
    }

    public List<NameValuePair> getValuePairs() {
        return this.nameValuePairs;
    }
}
