package application.model.database;

import org.json.JSONException;
import org.json.JSONObject;

public class GetGroupInformation {
	
	GroupDAO getGroup = new GroupDAO();
	private String groupID, date, duration, subject, startTime, groupName;
	private Integer groupSize;
	private Double x, y;
	
	public GetGroupInformation() {
		/** TODO:
		  * We should make the group ID start sequentially in numerical order instead of depending 
		  *  upon the user's ID who created the group
		  *  Possibly implement a group owner/creater who then has additional properties such as editing.
		  *  The group creater will be the owner and also be put into the group 
		  **/
		
		getGroup.getGroupData();
		String JSONResponse = getGroup.getGroupData();
		try {
			JSONObject groupInfo = new JSONObject(JSONResponse);
			for(int i = 0; i < Integer.parseInt(groupInfo.getJSONObject("available").get("numberofgroups").toString()); i++)
			{
				groupID = groupInfo.getJSONObject("group" + i).get("groupid").toString();
				date = groupInfo.getJSONObject("group" + i).getString("date").toString();
				String parts[];
				parts = date.split("-");
				date = parts[1] + "/" + parts[2] + "/" + parts[0];
				duration = groupInfo.getJSONObject("group" + i).getString("duration").toString();
				subject = groupInfo.getJSONObject("group" + i).getString("studysubject").toString();
				startTime = groupInfo.getJSONObject("group" + i).getString("start").toString();
				x = groupInfo.getJSONObject("group" + i).getDouble("x");
				groupSize = groupInfo.getJSONObject("group" + i).getInt("groupsize");
				y = groupInfo.getJSONObject("group" + i).getDouble("y");
				groupName = groupInfo.getJSONObject("group" + i).getString("groupname").toString();
				
				System.out.println(groupID + "\n" + date + "\n" + duration + "\n" + subject);
				System.out.println(startTime + "\n" + x + "\n" + y + "\n" + groupSize + "\n" + groupName + "\n");
				//System.out.println(groupInfo.getJSONObject("group" + i).toString());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public boolean checkUserGroup(String userID)
	{
		String JSONResponse = getGroup.getGroupData();
		try {
			JSONObject groupInfo = new JSONObject(JSONResponse);
			for(int i = 0; i < Integer.parseInt(groupInfo.getJSONObject("available").get("numberofgroups").toString()); i++)
			{
				groupID = groupInfo.getJSONObject("group" + i).get("groupid").toString();
				if(userID.equals(groupID))
				{
					return true;
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
}
