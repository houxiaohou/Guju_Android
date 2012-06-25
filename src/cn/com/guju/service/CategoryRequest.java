package cn.com.guju.service;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class CategoryRequest {

	private String requestUrl;
	private JSONObject jsonObj;

	public JSONObject request(String styleID, String spaceID, int offset)
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
			jsonObj = new JSONObject(strResult);	
		}
		return jsonObj;
	}

}
