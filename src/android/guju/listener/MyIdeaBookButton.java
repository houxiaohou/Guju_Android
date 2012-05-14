package android.guju.listener;

import android.app.Activity;
import android.guju.R;
import android.guju.service.LoadLocalBitmap;
import android.guju.service.SystemApplication;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class MyIdeaBookButton {

	private Button myIdeaButton;
	private LoadLocalBitmap loadLocal;

	public void addMyIdeaButtonListener(final Activity activity, final int i,
			final ImageView imageView, final ViewFlipper viewFlipper) {
		myIdeaButton = (Button) activity.findViewById(R.id.myidea);
		myIdeaButton.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				SystemApplication.getInstance().setMyIdeaStatus(true);
				loadLocal = new LoadLocalBitmap();
				loadLocal.loadLocalPic(activity, imageView, viewFlipper, i);
			}
		});
	}

}
