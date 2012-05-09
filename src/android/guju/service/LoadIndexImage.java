package android.guju.service;

import android.app.Activity;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class LoadIndexImage {

	public void loadIndexImage(Activity activity, ImageView imageView,
			ViewFlipper viewFlipper, int imageId) {
		Bitmap bitmap = SystemApplication.getInstance().getBitmapFromMemCache(imageId);
		imageView = new ImageView(activity);
		if (bitmap != null) {
			
			imageView.setImageBitmap(bitmap);
			imageView.setScaleType(ImageView.ScaleType.CENTER);
			// viewFlipper.removeAllViews();
			viewFlipper.addView(imageView);
			//new LoadImageTask(imageView).execute(imageId+1);
		} else {
			new LoadImageTask(imageView).execute(imageId);
			imageView.setImageBitmap(bitmap);
			imageView.setScaleType(ImageView.ScaleType.CENTER);
			// viewFlipper.removeAllViews();
			viewFlipper.addView(imageView);
			//new LoadImageTask(imageView).execute(imageId+1);
		}
	}

}
