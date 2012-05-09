package android.guju.service;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class LoadImage {

	public void loadImage(int n, ImageView imageView, ViewFlipper viewFlipper,
			Activity activity, ArrayList<String> spaceIds)
			throws Exception {

		int imageId = Integer.parseInt(spaceIds.get(n));
		final Bitmap bitmap = SystemApplication.getInstance().getBitmapFromMemCache(imageId);
		imageView = new ImageView(activity);
		if (bitmap != null) {
			imageView.setImageBitmap(bitmap);
			imageView.setScaleType(ImageView.ScaleType.CENTER);
			viewFlipper.removeAllViews();
			viewFlipper.addView(imageView);
			new LoadImageTask(imageView).execute(Integer.parseInt(spaceIds.get(n+1)));
		} else {
			//LoadImageTask task = new LoadImageTask(iv);
			new LoadImageTask(imageView).execute(imageId);
			imageView.setImageBitmap(bitmap);
			imageView.setScaleType(ImageView.ScaleType.CENTER);
			viewFlipper.removeAllViews();
			viewFlipper.addView(imageView);
			new LoadImageTask(imageView).execute(Integer.parseInt(spaceIds.get(n+1)));
		}

	}

}
