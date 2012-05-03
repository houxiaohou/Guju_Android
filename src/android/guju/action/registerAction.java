package android.guju.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class registerAction {
	
	public static void main(String[] args) throws Exception{
		getUserInfo();
	}
	
	public static void getUserInfo() throws Exception, IOException{
		
		/**
		EditText emailText = (EditText) activity.findViewById(R.id.email); 
		EditText usernameText = (EditText) activity.findViewById(R.id.username);
		EditText passwordText = (EditText) activity.findViewById(R.id.password);
		
		String email = emailText.getText().toString();
		String username = usernameText.getText().toString();
		String password = passwordText.getText().toString();
		*/
		String email = "jackiehou@173.com";
		String username = "houxiyang";
		String password = "900717";
		
		try {
			String enEmail = URLEncoder.encode(email,HTTP.UTF_8);
			String enUser = URLEncoder.encode(username,HTTP.UTF_8);
			String enPass = URLEncoder.encode(password,HTTP.UTF_8);
			String requestUrl = "http://www.guju.com.cn/authorize/u="+enUser+"/e="+enEmail+"/pwd="+enPass+"/t=2/k=/f=/l=/op=c";
			HttpPost httpRequest = new HttpPost(requestUrl);
			HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);
			if(httpResponse.getStatusLine().getStatusCode() == 200){
				String strResult = EntityUtils.toString(httpResponse.getEntity());
				System.out.print(strResult);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
