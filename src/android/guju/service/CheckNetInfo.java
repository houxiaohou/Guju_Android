package android.guju.service;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.os.Environment;
import android.provider.Settings;
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
			/**
			File sd = Environment.getExternalStorageDirectory();
			String path = sd.getPath() + "/Guju/";
			File file = new File(path);
			File[] files = file.listFiles();
			if (files.length != 0) {
			*/
				ToastLayout toast = new ToastLayout();
				String text = "还没联网呢，显示的是灵感集的图片哦~";
				toast.showToast(activity, text);
				SystemApplication.getInstance().setMyIdeaStatus(true);
				LoadLocalBitmap loadLocal = new LoadLocalBitmap();
				loadLocal.loadLocalPic(activity, imageView, viewFlipper, i);
				/**
			} else {
				AlertDialog subDialog = new AlertDialog.Builder(activity)
						.setTitle("还没有联网呢！")
						.setPositiveButton("打开网络", new OnClickListener() {

							public void onClick(DialogInterface arg0, int arg1) {
								// TODO Auto-generated method stub
								activity.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
							}
						}).setNegativeButton("不，我要退出", new OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								activity.finish();
							}

						}).create();
				subDialog.show();
			}
			*/
		}

	}
}
