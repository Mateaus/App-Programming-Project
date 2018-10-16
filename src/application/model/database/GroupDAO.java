package application.model.database;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import HttpRequests.CreateGroupRequest;
import HttpRequests.HttpHandler;

public class GroupDAO {
	
	// TODO: Finish GroupDAO after meeting...
	public void createGroup(Group group) {
		try {
			System.out.println(group.getGroupOwner());
			System.out.println(group.getNameOfGroup());
			System.out.println(group.getStudySubject());
			System.out.println(group.getSizeOfGroup());
			System.out.println(group.getStudyDate());
			System.out.println(group.getStart());
			System.out.println(group.getLength());
			System.out.println(group.getXLocation());
			System.out.println(group.getYLocation());
			CreateGroupRequest createGroup = new CreateGroupRequest(group.getGroupOwner(),group.getNameOfGroup(), group.getStudySubject()
					, group.getSizeOfGroup(), group.getStudyDate(), group.getStart(), group.getLength()
					, Double.toString(group.getXLocation()), Double.toString(group.getYLocation()));
			HttpHandler httpHandler = new HttpHandler(createGroup.getCreateGroupRequestUrl(), createGroup.getValuePairs());
    		HttpResponse httpResponse = httpHandler.HttpResponseRequest(httpHandler.HttpPostRequest());
    		String responsejson = EntityUtils.toString(httpResponse.getEntity());
    		EntityUtils.consume(httpResponse.getEntity());
    		
    		System.out.println(responsejson);
			
		} catch(Exception e) {
			System.out.println(e);
		}
	}

}
