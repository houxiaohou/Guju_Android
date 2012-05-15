package android.guju.service;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class CheckNetInfo {

	public void checkNet(final Activity activity, ImageView imageView,
			ViewFlipper viewFlipper, int i) {

		ConnectivityManager conMan = (ConnectivityManager) activity
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		State mobile = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
				.getState();
		State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.getState();

		if (wifi == State.DISCONNECTED && mobile == State.DISCONNECTED) {

				ToastLayout toast = new ToastLayout();
				String text = "还没联网呢，显示的是灵感集的图片哦~";
				toast.showToast(activity, text);
				SystemApplication.getInstance().setMyIdeaStatus(true);
				LoadLocalBitmap loadLocal = new LoadLocalBitmap();
				loadLocal.loadLocalPic(activity, imageView, viewFlipper, i);

		}

	}
}
