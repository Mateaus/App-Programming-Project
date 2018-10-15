package HttpRequests;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class CreateGroupRequest {
	private static final String CREATE_GROUP_REQUEST_URL = "http://last-minute-screws.000webhostapp.com/CreateGroup.php";
    private List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(6);

    public CreateGroupRequest(String group_name, String study_subject
    		, String group_size, LocalDate localDate, String start_time, String duration) {
        nameValuePairs.add(new BasicNameValuePair("group_name", group_name));
        nameValuePairs.add(new BasicNameValuePair("study_subject", study_subject));
        nameValuePairs.add(new BasicNameValuePair("group_size", group_size));
        nameValuePairs.add(new BasicNameValuePair("date", localDate.toString()));
        nameValuePairs.add(new BasicNameValuePair("start_time", start_time));
        nameValuePairs.add(new BasicNameValuePair("duration", duration));
    }

    public String getCreateGroupRequestUrl() {
        return CREATE_GROUP_REQUEST_URL;
    }

    public List<NameValuePair> getValuePairs() {
        return this.nameValuePairs;
    }
}
