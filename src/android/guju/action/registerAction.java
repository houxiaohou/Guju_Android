package android.guju.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.guju.R;
import android.widget.EditText;

public class registerAction {

	public void getUserInfo(Activity activity) throws Exception,
			IOException {

		EditText emailText = (EditText) activity.findViewById(R.id.email);
		EditText usernameText = (EditText) activity.findViewById(R.id.username);
		EditText passwordText = (EditText) activity.findViewById(R.id.password);

		String email = emailText.getText().toString();
		String username = usernameText.getText().toString();
		String password = passwordText.getText().toString();

		if (emailText != null && usernameText != null && passwordText != null) {
			try {
				String enEmail = URLEncoder.encode(email, HTTP.UTF_8);
				String enUser = URLEncoder.encode(username, HTTP.UTF_8);
				String enPass = URLEncoder.encode(password, HTTP.UTF_8);
				String requestUrl = "http://www.guju.com.cn/authorize/u="
						+ enUser + "/e=" + enEmail + "/pwd=" + enPass
						+ "/t=1/k=/f=/l=/op=c/__=1336014768901/ajaxRequestId=4";
				HttpPost httpRequest = new HttpPost(requestUrl);
				HttpResponse httpResponse = new DefaultHttpClient()
						.execute(httpRequest);
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					String strResult = EntityUtils.toString(httpResponse
							.getEntity());
					JSONObject jsonObj = new JSONObject(strResult);
					String error = jsonObj.getString("error");
					int errorNum = Integer.parseInt(error);
					if (errorNum == 0) {
						new AlertDialog.Builder(activity)
								.setTitle(R.string.regSuccess)
								.setMessage(R.string.regInfo)
								.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
									
									public void onClick(DialogInterface dialog, int which) {
										// TODO Auto-generated method stub
										
									}
								}).show();
					}
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
