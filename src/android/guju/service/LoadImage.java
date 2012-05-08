package android.guju.service;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class LoadImage {

	public void loadImage(int n, ImageView iv, ViewFlipper viewFlipper,
			Activity activity, ImageCache cache, ArrayList<String> spaceIds)
			throws Exception {

		int imageId = Integer.parseInt(spaceIds.get(n));
		final Bitmap bitmap = cache.getBitmapCache(imageId);
		iv = new ImageView(activity);
		if (bitmap != null) {
			iv.setImageBitmap(bitmap);
			iv.setScaleType(ImageView.ScaleType.CENTER);
			viewFlipper.removeAllViews();
			viewFlipper.addView(iv);
		} else {
			LoadImageTask task = new LoadImageTask(iv);
			task.execute(imageId);
			iv.setImageBitmap(bitmap);
			iv.setScaleType(ImageView.ScaleType.CENTER);
			viewFlipper.removeAllViews();
			viewFlipper.addView(iv);
		}

	}

}
