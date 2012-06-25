package cn.com.guju.listener;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.ViewFlipper;
import cn.com.guju.R;
import cn.com.guju.Async.LoadImageTask;
import cn.com.guju.service.CategoryRequest;
import cn.com.guju.service.JSONResolver;
import cn.com.guju.service.SystemApplication;
import cn.com.guju.service.SystemConstant;
import cn.com.guju.service.ToastLayout;

public class SpinnerListener {

	private Spinner styleSpinner;
	private Spinner spaceSpinner;
	private String spaceId = "0";
	private String styleId = "0";
	private Activity activity;
	private CategoryRequest request;
	private ArrayList<String> spaceIds;
	private JSONObject jsonObj;
	private JSONResolver jsonResolver;
	private int MSG_SUCCESS = 0;
	private int MSG_FAIL = 1;
	private String[] spaces;
	private String[] styles;
	private ViewFlipper viewFlipper;
	private ProgressBar progressBar;
	private ImageView iv;
	private Bitmap bitmap;
	private HashMap<String, String> spinnerInfo = null;

	public SpinnerListener(Activity activity, String[] styles, String[] spaces,
			ImageView iv, ViewFlipper viewFlipper, ProgressBar progressBar) {
		this.activity = activity;
		this.styles = styles;
		this.spaces = spaces;
		this.iv = iv;
		this.viewFlipper = viewFlipper;
		this.progressBar = progressBar;
		spinnerInfo = new HashMap<String, String>();
	}

	public HashMap<String, String> clickListener() {
		styleSpinner = (Spinner) activity.findViewById(R.id.style);
		spaceSpinner = (Spinner) activity.findViewById(R.id.space);

		styleSpinner
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						String style = styles[arg2];
						styleId = SystemConstant.STYLES_ID_MAP.get(style);
						Log.i("styleId", styleId);

						spinnerClick();

					}

					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
					}

				});

		spaceSpinner
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						String space = spaces[arg2];
						spaceId = SystemConstant.CATEGORIES_ID_MAP.get(space);
						Log.i("spaceId", spaceId);

						spinnerClick();

					}

					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
					}

				});
		spinnerInfo.put("styleId", styleId);
		spinnerInfo.put("spaceId", spaceId);
		return spinnerInfo;
	}

	public void spinnerClick() {
		SystemApplication.getInstance().setMyIdeaStatus(false);
		if (styleId.equals("0") && spaceId.equals("0")) {
			SystemApplication.getInstance().setStyleStatus(false);
			SystemApplication.getInstance().setSpaceStatus(false);
		} else if (!styleId.equals("0") && spaceId.equals("0")) {
			SystemApplication.getInstance().setStyleStatus(true);
			SystemApplication.getInstance().setSpaceStatus(false);

			viewFlipper.removeAllViews();
			viewFlipper.addView(progressBar);
			Thread thread = new Thread(runnable);
			thread.start();
		} else if (styleId.equals("0") && !spaceId.equals("0")) {
			SystemApplication.getInstance().setStyleStatus(false);
			SystemApplication.getInstance().setSpaceStatus(true);

			viewFlipper.removeAllViews();
			viewFlipper.addView(progressBar);
			Thread thread = new Thread(runnable);
			thread.start();
		} else {
			SystemApplication.getInstance().setStyleStatus(true);
			SystemApplication.getInstance().setSpaceStatus(true);

			viewFlipper.removeAllViews();
			viewFlipper.addView(progressBar);
			Thread thread = new Thread(runnable);
			thread.start();
		}
	}

	Runnable runnable = new Runnable() {

		public void run() {
			// TODO Auto-generated method stub
			Looper.prepare();
			try {
				request = new CategoryRequest();
				jsonResolver = new JSONResolver();
				jsonObj = request.request(styleId, spaceId, 0);
				spaceIds = jsonResolver.getSpaceIds(jsonObj);
				if (spaceIds.size() < 1) {
					mHandler.obtainMessage(MSG_FAIL).sendToTarget();
				} else {
					int imageId = Integer.parseInt(spaceIds.get(0));
					bitmap = SystemApplication.getInstance()
							.getBitmapFromMemCache(imageId);
					if (bitmap != null) {
						mHandler.obtainMessage(MSG_SUCCESS, bitmap)
								.sendToTarget();
						Log.i("imageId", Integer.toString(imageId));
					} else {
						bitmap = new LoadImageTask().execute(imageId).get();
						mHandler.obtainMessage(MSG_SUCCESS, bitmap)
						.sendToTarget();
					}

				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	};

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				iv = new ImageView(activity);
				iv.setImageBitmap((Bitmap) msg.obj);
				iv.setScaleType(ImageView.ScaleType.CENTER);
				viewFlipper.removeAllViews();
				viewFlipper.addView(iv);
				break;
			case 1:
				String message = "没有符合条件的结果~";
				new ToastLayout().showToast(activity, message);
			}
		}
	};

}
