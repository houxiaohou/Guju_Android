package android.guju.service;

import java.io.File;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class LoadLocalBitmap {

	private Bitmap bitmap;
	private ToastLayout toast;

	public void loadLocalPic(Activity activity, ImageView imageView,
			ViewFlipper viewFlipper, int i) {

		File sd = Environment.getExternalStorageDirectory();
		String path = sd.getPath() + "/Guju/";
		File file = new File(path);
		File[] files = file.listFiles();
		if (i < files.length && i >= 0) {
			String fileName = path + files[i].getName();
			bitmap = BitmapFactory.decodeFile(fileName);
			imageView = new ImageView(activity);
			imageView.setImageBitmap(bitmap);
			imageView.setScaleType(ImageView.ScaleType.CENTER);
			viewFlipper.removeAllViews();
			viewFlipper.addView(imageView);
		} else if (i >= files.length) {
			String text = "最后一张了哦~";
			toast = new ToastLayout();
			toast.showToast(activity, text);
		} else if (i < 0) {
			String text = "前面没有啦~";
			toast = new ToastLayout();
			toast.showToast(activity, text);
		}

	}

}
