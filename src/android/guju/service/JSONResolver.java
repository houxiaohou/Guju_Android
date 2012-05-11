package android.guju.service;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONResolver {

	private ArrayList<String> spaceIds = new ArrayList<String>();
	private int availableResults;
	private String isSuccess;

	public int getAvailableResults(JSONObject jsonObj) throws Exception,
			JSONException {
		availableResults = Integer.parseInt(jsonObj
				.getString("availableResults"));
		return availableResults;
	}

	public ArrayList<String> getSpaceIds(JSONObject jsonObj)
			throws JSONException {
		JSONArray results = jsonObj.getJSONArray("results");
		isSuccess = jsonObj.getString("success");
		if(isSuccess.equals("true")){
			for (int i = 0; i < results.length(); i++) {
				JSONObject resultsObj = new JSONObject(results.getString(i));
				String spaceId = resultsObj.getString("spaceId");
				spaceIds.add(spaceId);
			}
		}
		return spaceIds;
	}

}
