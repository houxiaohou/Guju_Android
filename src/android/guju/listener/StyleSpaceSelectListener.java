package android.guju.listener;

import java.util.ArrayList;

import android.app.Activity;
import android.guju.R;
import android.guju.service.CategoryRequest;
import android.guju.service.SystemConstant;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

public class StyleSpaceSelectListener {
	
	private Spinner styleSpinner;
	private Spinner spaceSpinner;
	private String styleId;
	private String spaceId;
	private CategoryRequest categoryRequest;
	
	
	public ArrayList<String> addSpinnerListener(final Activity activity, final String[] styles, final String[] spaces, final int offset) throws Exception{
		
		styleSpinner = (Spinner) activity.findViewById(R.id.style);
		spaceSpinner = (Spinner) activity.findViewById(R.id.space);
		
		styleSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

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
		
		spaceSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

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
		
		categoryRequest = new CategoryRequest();
		return categoryRequest.request(styleId, spaceId, activity, offset);
	}
	
}
