package cn.com.guju.action;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.com.guju.service.SystemApplication;
import cn.com.guju.service.SystemConstant;
import cn.com.guju.service.ToastLayout;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

public class AddIdeaAction {

	private SharedPreferences preferences;
	
	public void addIdea(Activity activity) throws IOException {
		String id = SystemApplication.getInstance().getBitmapId();
		preferences = activity.getSharedPreferences("user_ideaBook",
				Context.MODE_PRIVATE);

		if (!preferences.contains(id) && id != null) {
			savePic(id, activity);
			
			Editor editor = preferences.edit();
			editor.putString(id, "value");
			editor.commit();
		} else {
			String text = "你已经添加过了~";
			new ToastLayout().showToast(activity, text);
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
				URL imgUrl = new URL(SystemConstant.BASE_URL+ SystemConstant.SIMAGES + id
						+ "_0_9-.jpg");
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
		}else{
			ToastLayout toast = new ToastLayout();
			String text = "没安装SD卡，无法加入~";
			toast.showToast(activity, text);
		}
	}

}
