package android.guju.listener;

import android.app.Activity;
import android.guju.R;
import android.view.View;
import android.widget.Button;

public class StyleButton {
	private Button styleButton;
	
	public void addStyleButtonControl(Activity activity){
		styleButton = (Button) activity.findViewById(R.id.style);
		styleButton.setOnClickListener(new Button.OnClickListener(){

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	
}
