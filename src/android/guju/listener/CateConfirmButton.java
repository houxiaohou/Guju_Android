package android.guju.listener;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import android.app.Activity;
import android.guju.R;
import android.guju.service.CategoryRequest;
import android.guju.service.JSONResolver;
import android.guju.service.LoadImage;
import android.guju.service.SystemApplication;
import android.guju.service.SystemConstant;
import android.guju.service.ToastLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.ViewFlipper;

public class CateConfirmButton {

	private Button cateConfirmButt;
	private Spinner styleSpinner;
	private Spinner spaceSpinner;
	private String spaceId;
	private String styleId;
	private LoadImage loadImage;
	private CategoryRequest request;
	private ArrayList<String> spaceIds;
	private HashMap<String, String> spinnerInfo = new HashMap<String, String>();
	private JSONObject jsonObj;
	private JSONResolver jsonResolver;

	public HashMap<String, String> addCateButtonListener(
			final Activity activity, final String[] styles,
			final String[] spaces, final ImageView iv,
			final ViewFlipper viewFlipper) throws Exception {
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

		cateConfirmButt = (Button) activity.findViewById(R.id.confirmButt);
		cateConfirmButt.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				SystemApplication.getInstance().setStatus(true);
				loadImage = new LoadImage();
				request = new CategoryRequest();
				jsonResolver = new JSONResolver();
				try {
					jsonObj = request.request(styleId, spaceId, 0);
					spaceIds = jsonResolver.getSpaceIds(jsonObj);
					if(spaceIds.size() == 0){
						new ToastLayout().showToast(activity, "没有符合条件的图片哦~");
					}else{
						loadImage.loadImage(0, iv, viewFlipper, activity,
								spaceIds);
						SystemApplication.getInstance().setBitmapId(spaceIds.get(0));
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				spinnerInfo.put("styleId", styleId);
				spinnerInfo.put("spaceId", spaceId);
			}

		});

		return spinnerInfo;
	}

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
