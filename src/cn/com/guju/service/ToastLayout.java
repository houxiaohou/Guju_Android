package cn.com.guju.service;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import cn.com.guju.R;

public class ToastLayout {

	public void showToast(Activity activity, String message) {

		LayoutInflater inflater = activity.getLayoutInflater();
		View layout = inflater.inflate(R.layout.toast, (ViewGroup) activity.findViewById(R.id.toast_root));
		TextView text = (TextView) layout.findViewById(R.id.toast_text);
		layout.getBackground().setAlpha(200);
		text.setText(message);
		Toast toast = new Toast(activity);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setView(layout);
		toast.show();
	}

}
