package cn.com.guju.listener;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewFlipper;
import cn.com.guju.R;
import cn.com.guju.service.LoadLocalBitmap;
import cn.com.guju.service.SystemApplication;

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
