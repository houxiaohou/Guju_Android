package android.guju.Async;

import java.util.ArrayList;

import org.json.JSONObject;

import android.graphics.Bitmap;
import android.guju.service.CategoryRequest;
import android.guju.service.JSONResolver;
import android.os.Handler;

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
			bitmap = new AsyncLoadTask(imageId).execute().get();
			handler.obtainMessage(MSG_SUCCESS, bitmap).sendToTarget();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
