package android.guju.listener;

import java.util.ArrayList;

import org.json.JSONObject;

import android.app.Activity;
import android.guju.R;
import android.guju.service.CategoryRequest;
import android.guju.service.JSONResolver;
import android.guju.service.SystemConstant;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

public class StyleSpaceSelectListener {

	private Spinner styleSpinner;
	private Spinner spaceSpinner;
	private String spaceId;
	private String styleId;
	private CategoryRequest categoryRequest = new CategoryRequest();
	private ArrayList<String> spaceIds;
	private JSONResolver jsonResolver;
	private JSONObject jsonObj;

	public ArrayList<String> addSpinnerListener(final Activity activity,
			final String[] styles, final String[] spaces, final int offset)
			throws Exception {

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
						styleId = "0";
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
						spaceId = "0";
					}

				});
		System.out.print(styleId);
		if (styleId==null) {
			jsonResolver = new JSONResolver();
			jsonObj = categoryRequest.request("0", "0", offset);
			spaceIds = jsonResolver.getSpaceIds(jsonObj);
			return spaceIds;
		}else{
			jsonResolver = new JSONResolver();
			jsonObj = categoryRequest.request(styleId, spaceId, offset);
			spaceIds = jsonResolver.getSpaceIds(jsonObj);
			return spaceIds;
		}
	}

}
