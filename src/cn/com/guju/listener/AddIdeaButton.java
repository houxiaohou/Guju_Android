package cn.com.guju.listener;

import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import cn.com.guju.R;
import cn.com.guju.action.AddIdeaAction;
import cn.com.guju.action.LoginAction;

public class AddIdeaButton {

	private LoginAction loginAction = null;
	private RegisterButton submitButton = null;
	private SharedPreferences preferences = null;

	public void addIdeaButtonListener(final Activity activity) {

		View addIdeaButton = (View) activity.findViewById(R.id.addidea);
		addIdeaButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				preferences = activity.getSharedPreferences("GujuAPP_userInfo",
						Context.MODE_PRIVATE);
				String email = preferences.getString("email", "");
				String password = preferences.getString("password", "");
				if (email.length() != 0 && email != null && password != null
						&& password.length() != 0) {
					try {
						new AddIdeaAction().addIdea(activity);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					LayoutInflater factory = LayoutInflater.from(activity);
					final View regView = factory.inflate(R.layout.submit, null);

					AlertDialog subDialog = new AlertDialog.Builder(activity)
							.setTitle("先登录再加入灵感集吧").setView(regView)
							.setPositiveButton("点击登录", new OnClickListener() {

								public void onClick(DialogInterface arg0,
										int arg1) {
									// TODO Auto-generated method stub
									try {
										loginAction = new LoginAction();
										loginAction.login(regView, activity);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}

							}).setNegativeButton("没有账号", new OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									submitButton = new RegisterButton();
									submitButton.registerListener(activity);
								}

							}).create();
					subDialog.show();
				}
			}
		});
	}
}
