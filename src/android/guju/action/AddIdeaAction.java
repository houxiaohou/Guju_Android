package android.guju.action;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.guju.service.SystemApplication;
import android.guju.service.ToastLayout;

public class AddIdeaAction {

	private SharedPreferences preferences;

	public void addIdea(Activity activity) {
		String id = SystemApplication.getInstance().getBitmapId();
		preferences = activity.getSharedPreferences("user_ideaBook",
				Context.MODE_PRIVATE);
		
		if (!preferences.contains(id)) {
			String text = "添加成功！";
			new ToastLayout().showToast(activity, text);
			Editor editor = preferences.edit();
			editor.putString(id, "value");
			editor.commit();
		}else{
			String text = "你已经添加过了~";
			new ToastLayout().showToast(activity, text);
		}
		
		
	}
}
