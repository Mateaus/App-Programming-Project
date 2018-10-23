package application.model.database;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import HttpRequests.CreateGroupRequest;
import HttpRequests.GetGroupRequests;
import HttpRequests.HttpHandler;

public class GroupDAO {
	
	// TODO: Finish GroupDAO after meeting...
	public void createGroup(Group group) {
		try {
		
			CreateGroupRequest createGroup = new CreateGroupRequest(group.getGroupOwner(),group.getNameOfGroup(), group.getStudySubject()
					, group.getSizeOfGroup(), group.getStudyDate(), group.getStart(), group.getLength()
					, Double.toString(group.getXLocation()), Double.toString(group.getYLocation()));
			HttpHandler httpHandler = new HttpHandler(createGroup.getCreateGroupRequestUrl(), createGroup.getValuePairs());
    		HttpResponse httpResponse = httpHandler.HttpResponseRequest(httpHandler.HttpPostRequest());
    		String responsejson = EntityUtils.toString(httpResponse.getEntity());
    		EntityUtils.consume(httpResponse.getEntity());
    		
    		//System.out.println(responsejson);
			
		} catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public String getGroupData(){
		try{
			GetGroupRequests getGroup = new GetGroupRequests();
			HttpHandler httpHandler = new HttpHandler(getGroup.getGroupRequestUrl(), getGroup.getValuePairs());
    		HttpResponse httpResponse = httpHandler.HttpResponseRequest(httpHandler.HttpPostRequest());
    		String responsejson = EntityUtils.toString(httpResponse.getEntity());
    		EntityUtils.consume(httpResponse.getEntity());
    		return responsejson;
		}
		catch (Exception e){
			return null;
		}
	}
}
