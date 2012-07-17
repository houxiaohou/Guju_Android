package cn.com.guju.Async;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONObject;

import cn.com.guju.service.CategoryRequest;
import cn.com.guju.service.JSONResolver;
import cn.com.guju.service.SystemApplication;
import cn.com.guju.service.SystemConstant;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;

public class RequestRunnable implements Runnable {

	private CategoryRequest request;
	private JSONResolver jsonResolver;
	private JSONObject jsonObj;
	private ArrayList<String> spaceIds;
	private int n;
	private int imageId;
	private Bitmap bitmap;
	private Handler handler;
	private final static int MSG_SUCCESS = 0;

	public RequestRunnable(String styleId, String spaceId, int index, int n,
			Handler handler) {
		this.n = n;
		this.handler = handler;
		try {
			request = new CategoryRequest();
			jsonResolver = new JSONResolver();
			jsonObj = request.request(styleId, spaceId, index);
			spaceIds = jsonResolver.getSpaceIds(jsonObj);
			SystemApplication.getInstance().setSpaceIds(spaceIds);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run() {
		// TODO Auto-generated method stub
		try {
			imageId = Integer.parseInt(SystemApplication.getInstance().getSpaceIds().get(n));
			bitmap = SystemApplication.getInstance().getBitmapFromMemCache(
					imageId);
			System.out.println("iamgeId--" + imageId);
			System.out.println("spaceIds--" + spaceIds);
			if (bitmap != null) {
				handler.obtainMessage(MSG_SUCCESS, bitmap).sendToTarget();
				Log.i("imageId", Integer.toString(imageId));
			} else {
				URL imgUrl = null;
				try {
					imgUrl = new URL(SystemConstant.BASE_URL
							+ SystemConstant.SIMAGES + imageId
							+ SystemConstant.PHOTO_VERSION);
					HttpURLConnection conn = (HttpURLConnection) imgUrl
							.openConnection();
					conn.connect();
					InputStream is = conn.getInputStream();
					bitmap = BitmapFactory.decodeStream(is);
					SystemApplication.getInstance().addBitmapToMemoryCache(
							imageId, bitmap);
					SystemApplication.getInstance().setBitmapId(spaceIds.get(n));
					is.close();

				} catch (Exception e) {
					e.printStackTrace();
				}
				handler.obtainMessage(MSG_SUCCESS, bitmap).sendToTarget();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
