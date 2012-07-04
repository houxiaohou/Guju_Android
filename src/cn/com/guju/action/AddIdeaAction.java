package cn.com.guju.action;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import cn.com.guju.service.SystemApplication;
import cn.com.guju.service.SystemConstant;
import cn.com.guju.service.ToastLayout;

public class AddIdeaAction {

	public void addIdea(Activity activity, String email, String galleryId)
			throws Exception {
		String id = SystemApplication.getInstance().getBitmapId();
		String gtitle = "我的手机灵感集";
		String enGtitle = URLEncoder.encode(gtitle, HTTP.UTF_8);
		String requestUrl = SystemConstant.BASE_URL
				+ SystemConstant.ADDIDEABOOK + "email=" + email + "&id=" + id
				+ "&gtitle=" + enGtitle + "&gid=" + galleryId;
		HttpPost httpRequest = new HttpPost(requestUrl);
		HttpResponse httpResponse = new DefaultHttpClient()
				.execute(httpRequest);
		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			String strResult = EntityUtils.toString(httpResponse
					.getEntity());
			JSONObject jsonObj = new JSONObject(strResult);
			String error = jsonObj.getString("error");
			if(error.equals("0")){
				savePic(id, activity);
				String gid = jsonObj.getString("galleryId");
				SharedPreferences sharedPreferences = activity.getSharedPreferences("GujuAPP_userInfo", Context.MODE_PRIVATE);
				Editor editor = sharedPreferences.edit();
				editor.putString("galleryId", gid);
				editor.commit();
			}else if (error.equals("1")){
				String text = "用户不存在！";
				new ToastLayout().showToast(activity, text);
			} else if (error.equals("2")){
				String text = "已经添加过了！";
				new ToastLayout().showToast(activity, text);
			} else if (error.equals("3")) {
				String text = "图片不存在！";
				new ToastLayout().showToast(activity, text);
			} else {
				String text = "出错了~";
				new ToastLayout().showToast(activity, text);
			}
		}
	}

	public void savePic(String id, Activity activity) throws IOException {
		File sd = Environment.getExternalStorageDirectory();
		if (sd.canWrite()) {
			String path = sd.getPath() + "/Guju/";
			File file = new File(path);
			if (!file.exists()) {
				file.mkdir();
			}
			File imageFile = new File(path + id + ".jpg");
			Bitmap bitmap = null;
			try {
				URL imgUrl = new URL(SystemConstant.BASE_URL
						+ SystemConstant.SIMAGES + id
						+ SystemConstant.PHOTO_VERSION);
				HttpURLConnection conn = (HttpURLConnection) imgUrl
						.openConnection();
				conn.connect();
				InputStream is = conn.getInputStream();
				BitmapFactory.Options ops = new BitmapFactory.Options();
				ops.inSampleSize = 3;
				bitmap = BitmapFactory.decodeStream(is, null, ops);
			} catch (Exception e) {
				e.printStackTrace();
			}
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(imageFile));
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
			bos.flush();
			bos.close();
			String text = "添加成功！";
			new ToastLayout().showToast(activity, text);
		} else {
			ToastLayout toast = new ToastLayout();
			String text = "没安装SD卡，无法加入~";
			toast.showToast(activity, text);
		}
	}

}
