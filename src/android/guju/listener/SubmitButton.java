package android.guju.listener;

import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.guju.R;
import android.guju.action.registerAction;
import android.view.LayoutInflater;
import android.view.View;

public class SubmitButton {

	private registerAction regAction = null;

	public void submitListener(final Activity activity) {

		LayoutInflater factory = LayoutInflater.from(activity);
		final View subView = factory.inflate(R.layout.register, null);

		AlertDialog regDialog = new AlertDialog.Builder(activity)
				.setTitle("用户注册").setView(subView)
				.setPositiveButton("提交", new OnClickListener() {

					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub

						try {
							regAction = new registerAction();
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
