package android.guju.action;

import java.io.File;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

public class MyIdeaBookAction {
	
	private Bitmap bitmap;
	
	public Bitmap ideaBookBitmap(Activity activity, int i){
		File sd = Environment.getExternalStorageDirectory();
		String path = sd.getPath() + "/Guju/";
		File file = new File(path);
		File[] files = file.listFiles();
		if (i <= files.length) {
			String fileName = path + files[i].getName();
			bitmap = BitmapFactory.decodeFile(fileName);
		}
		return bitmap;
	}
	
}
