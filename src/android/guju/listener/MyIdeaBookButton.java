package android.guju.listener;

import android.app.Activity;
import android.guju.R;
import android.guju.service.LoadLocalBitmap;
import android.guju.service.SystemApplication;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class MyIdeaBookButton {

	private LoadLocalBitmap loadLocal;

	public void addMyIdeaButtonListener(final Activity activity, final int i,
			final ImageView imageView, final ViewFlipper viewFlipper) {
		View myIdeaButton = (View) activity.findViewById(R.id.myidea);
		myIdeaButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				SystemApplication.getInstance().setValueOfn(0);
				SystemApplication.getInstance().setMyIdeaStatus(true);
				loadLocal = new LoadLocalBitmap();
				loadLocal.loadLocalPic(activity, imageView, viewFlipper, i);
			}
		});
	}

}
