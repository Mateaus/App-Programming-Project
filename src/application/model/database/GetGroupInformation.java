package application.model.database;

import org.json.JSONException;
import org.json.JSONObject;

public class GetGroupInformation {
	
	GroupDAO getGroup = new GroupDAO();
	public String groupID;
	
	public GetGroupInformation() {
		// TODO Auto-generated method stub
		getGroup.getGroupData();
		String JSONResponse = getGroup.getGroupData();
		try {
			JSONObject groupInfo = new JSONObject(JSONResponse);
			for(int i = 0; i < Integer.parseInt(groupInfo.getJSONObject("available").get("numberofgroups").toString()); i++)
			{
				groupID = groupInfo.getJSONObject("group" + i).get("groupid").toString();
				//System.out.println(groupID);
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
