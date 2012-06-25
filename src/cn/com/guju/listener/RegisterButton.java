package cn.com.guju.listener;

import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;
import cn.com.guju.R;
import cn.com.guju.action.RegisterAction;

public class RegisterButton {

	private RegisterAction regAction = null;
	

	public void registerListener(final Activity activity) {

		LayoutInflater factory = LayoutInflater.from(activity);
		final View subView = factory.inflate(R.layout.register, null);

		AlertDialog regDialog = new AlertDialog.Builder(activity)
				.setTitle("注册账号方便收藏").setView(subView)
				.setPositiveButton("提交", new OnClickListener() {

					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub

						try {
							regAction = new RegisterAction();
							regAction.getUserInfo(subView,activity);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}).setNegativeButton("取消", new OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}

				}).create();
		regDialog.show();
	}
}
