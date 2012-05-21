package android.guju.listener;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.guju.R;
import android.guju.Async.LoadImageTask;
import android.guju.service.CategoryRequest;
import android.guju.service.JSONResolver;
import android.guju.service.LoadImage;
import android.guju.service.SystemApplication;
import android.guju.service.SystemConstant;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.ViewFlipper;

public class CateConfirmButton {

	private Button cateConfirmButt;
	private Spinner styleSpinner;
	private Spinner spaceSpinner;
	private String spaceId;
	private String styleId;
	private Activity activity;
	private CategoryRequest request;
	private ArrayList<String> spaceIds;
	private HashMap<String, String> spinnerInfo = new HashMap<String, String>();
	private JSONObject jsonObj;
	private JSONResolver jsonResolver;
	private int MSG_SUCCESS = 0;
	private String[] spaces;
	private String[] styles;
	private ViewFlipper viewFlipper;
	private ProgressBar progressBar;
	private ImageView iv;
	private Bitmap bitmap;
	
	
	public CateConfirmButton(Activity activity, String[] styles,
			String[] spaces, ImageView iv,
			ViewFlipper viewFlipper, ProgressBar progressBar){
		this.activity = activity;
		this.styles = styles;
		this.spaces = spaces;
		this.viewFlipper = viewFlipper;
		this.iv = iv;
		this.progressBar = progressBar;
	}

	public HashMap<String, String> addCateButtonListener() throws Exception {
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
					}

					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
					}

				});

		cateConfirmButt = (Button) activity.findViewById(R.id.confirmButt);
		cateConfirmButt.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				SystemApplication.getInstance().setStatus(true);
				SystemApplication.getInstance().setMyIdeaStatus(false);
				
				viewFlipper.removeAllViews();
				viewFlipper.addView(progressBar);
				
				Thread thread = new Thread(runnable);
				thread.start();
				
				spinnerInfo.put("styleId", styleId);
				spinnerInfo.put("spaceId", spaceId);
			}

		});

		return spinnerInfo;
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
				int imageId = Integer.parseInt(spaceIds.get(0));
				bitmap = new LoadImageTask().execute(imageId).get();
				mHandler.obtainMessage(MSG_SUCCESS, bitmap).sendToTarget();
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
			}
		}
	};

	public HashMap<String, String> getSpinnerInfo(final Activity activity,
			final String[] styles, final String[] spaces) {
		styleSpinner = (Spinner) activity.findViewById(R.id.style);
		spaceSpinner = (Spinner) activity.findViewById(R.id.space);

		styleSpinner
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						String style = styles[arg2];
						styleId = SystemConstant.STYLES_ID_MAP.get(style);
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
					}

					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
					}

				});
		spinnerInfo.put("styleId", styleId);
		spinnerInfo.put("spaceId", spaceId);
		return spinnerInfo;
	}
}
