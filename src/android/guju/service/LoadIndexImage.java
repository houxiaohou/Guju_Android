package android.guju.service;

import android.app.Activity;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class LoadIndexImage {

	public void loadIndexImage(Activity activity, ImageView imageView,
			ViewFlipper viewFlipper, ImageCache cache, int imageId) {
		Bitmap bitmap = cache.getBitmapCache(imageId);
		imageView = new ImageView(activity);
		if (bitmap != null) {
			imageView.setImageBitmap(bitmap);
			imageView.setScaleType(ImageView.ScaleType.CENTER);
			// viewFlipper.removeAllViews();
			viewFlipper.addView(imageView);
		} else {
			LoadImageTask task = new LoadImageTask(imageView);
			task.execute(imageId);
			imageView.setImageBitmap(bitmap);
			imageView.setScaleType(ImageView.ScaleType.CENTER);
			// viewFlipper.removeAllViews();
			viewFlipper.addView(imageView);
		}
	}

}
