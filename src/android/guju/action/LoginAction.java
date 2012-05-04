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
import android.view.View;
import android.widget.EditText;

public class LoginAction {
	
	public void login(View view, Activity activity) throws Exception{
		
		EditText emailText = (EditText) view.findViewById(R.id.s_username);
		EditText passwordText = (EditText) view.findViewById(R.id.s_password);

		String email = emailText.getText().toString();
		String password = passwordText.getText().toString();
		
		if (emailText != null && passwordText != null) {
			try {
				String enEmail = URLEncoder.encode(email, HTTP.UTF_8);
				String enPass = URLEncoder.encode(password, HTTP.UTF_8);
				String requestUrl = "http://guju.com.cn/authorize/u="+enEmail+"/pwd="+enPass+"/k=y/op=l";
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
								.setMessage(R.string.subInfo)
								.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
									
									public void onClick(DialogInterface dialog, int which) {
										// TODO Auto-generated method stub
										
									}
								}).show();
						SharedPreferences sharedPreferences = activity.getSharedPreferences("GujuAPP_userInfo", Context.MODE_PRIVATE);
						Editor editor = sharedPreferences.edit();
						editor.putString("email", email);
						editor.putString("password", password);
						editor.commit();
						
					}
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
