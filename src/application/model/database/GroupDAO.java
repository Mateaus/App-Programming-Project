package application.model.database;

import HttpRequests.CreateGroupRequest;

public class GroupDAO {
	
	// TODO: Finish GroupDAO after meeting...
	public void createGroup(Group group) {
		try {
			
			CreateGroupRequest createGroup = new CreateGroupRequest(group.getNameOfGroup(), group.getStudySubject()
					, group.getSizeOfGroup(), group.getStudyDate(), group.getStart(), group.getLength());
			
		} catch(Exception e) {
			
		}
	}

}
