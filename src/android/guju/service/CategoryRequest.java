package android.guju.service;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class CategoryRequest {

	private ArrayList<String> spaceIds = new ArrayList<String>();
	private String requestUrl;

	public ArrayList<String> request(String styleID, String spaceID, int offset)
			throws Exception, IOException {
		if (styleID == null) {
			requestUrl = "http://guju.com.cn/getFeaturedSpaces/offset="
					+ offset
					+ "/cat=0/style=0/met=0/op=f/__=1336184615086/ajaxRequestId=1";
		} else {
			requestUrl = "http://guju.com.cn/getFeaturedSpaces/offset="
					+ offset + "/cat=" + spaceID + "/style=" + styleID
					+ "/met=0/op=f/__=1336184615086/ajaxRequestId=1";
		}
		HttpPost httpRequest = new HttpPost(requestUrl);
		HttpResponse httpResponse = new DefaultHttpClient()
				.execute(httpRequest);
		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			String strResult = EntityUtils.toString(httpResponse.getEntity());
			JSONObject jsonObj = new JSONObject(strResult);
			String isSuccess = jsonObj.getString("success");
			String success = "true";
			if (isSuccess.equals(success)) {
				JSONArray results = jsonObj.getJSONArray("results");
				for (int i = 0; i < results.length(); i++) {
					JSONObject resultsObj = new JSONObject(results.getString(i));
					String spaceId = resultsObj.getString("spaceId");
					spaceIds.add(spaceId);
				}
			}
		}
		return spaceIds;
	}

}
