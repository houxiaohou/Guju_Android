package android.guju.Async;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.guju.service.CategoryRequest;
import android.guju.service.JSONResolver;
import android.guju.service.SystemApplication;
import android.os.Handler;
import android.util.Log;

public class RequestRunnable implements Runnable {

	private CategoryRequest request;
	private JSONResolver jsonResolver;
	private JSONObject jsonObj;
	private ArrayList<String> spaceIds;
	private int n;
	private int index;
	private int imageId;
	private String styleId, spaceId;
	private Bitmap bitmap;
	private Handler handler;
	private final static int MSG_SUCCESS = 0;
	
	public RequestRunnable(String styleId, String spaceId, int index, int n, Handler handler) {
		this.styleId = styleId;
		this.spaceId = spaceId;
		this.n = n;
		this.index = index;
		this.handler = handler;
	}

	public void run() {
		// TODO Auto-generated method stub
		try {
			request = new CategoryRequest();
			jsonResolver = new JSONResolver();
			jsonObj = request.request(styleId, spaceId, index);
			spaceIds = jsonResolver.getSpaceIds(jsonObj);
			imageId = Integer.parseInt(spaceIds.get(n));
			bitmap = SystemApplication.getInstance()
					.getBitmapFromMemCache(imageId);
			if (bitmap != null) {
				handler.obtainMessage(MSG_SUCCESS, bitmap)
						.sendToTarget();
				Log.i("imageId", Integer.toString(imageId));
			} else {
				URL imgUrl = null;
				try {
					imgUrl = new URL("http://www.guju.com.cn/gimages/" + imageId
							+ "_0_9-.jpg");
					HttpURLConnection conn = (HttpURLConnection) imgUrl
							.openConnection();
					conn.connect();
					InputStream is = conn.getInputStream();
					BitmapFactory.Options ops = new BitmapFactory.Options();
					ops.inSampleSize = 3;
					bitmap = BitmapFactory.decodeStream(is, null, ops);
					SystemApplication.getInstance().addBitmapToMemoryCache(imageId,
							bitmap);
					is.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				handler.obtainMessage(MSG_SUCCESS, bitmap)
				.sendToTarget();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
