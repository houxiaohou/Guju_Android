package android.guju.action;

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
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.guju.R;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class RegisterAction {

	public void getUserInfo(View view, Activity activity) throws Exception{

		EditText emailText = (EditText) view.findViewById(R.id.email);
		EditText usernameText = (EditText) view.findViewById(R.id.username);
		EditText passwordText = (EditText) view.findViewById(R.id.password);

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
					String isSuccess = jsonObj.getString("success");
					String success = "true";

					if (isSuccess.equals(success)) {
						new AlertDialog.Builder(activity)
								.setTitle(R.string.regSuccess)
								.setMessage(R.string.regInfo)
								.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
									
									public void onClick(DialogInterface dialog, int which) {
										// TODO Auto-generated method stub
										
									}
								}).show();
						SharedPreferences sharedPreferences = activity.getSharedPreferences("GujuAPP_userInfo", Context.MODE_PRIVATE);
						Editor editor = sharedPreferences.edit();
						editor.putString("email", email);
						editor.putString("username", username);
						editor.putString("password", password);
						editor.commit();
						
					}
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			Log.d(username, "出错了~");
		}
	}

}
