package android.guju.service;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

public class LoadImageTask extends AsyncTask<Integer, Integer, Bitmap> {

	private final WeakReference<ImageView> imageViewReference;

	public LoadImageTask(ImageView imageView) {
		imageViewReference = new WeakReference<ImageView>(imageView);
	}

	@Override
	protected Bitmap doInBackground(Integer... m) {
		getBitmap(m[0]-1);
		getBitmap(m[0]+1);
		return getBitmap(m[0]);
	}

	@Override
	protected void onPostExecute(Bitmap bitmap) {
		if (imageViewReference != null && bitmap != null) {
			final ImageView imageView = (ImageView) imageViewReference.get();
			if (imageView != null) {
				imageView.setImageBitmap(bitmap);
			}
		}
	}
	
	public Bitmap getBitmap(int n){
		URL imgUrl = null;
		Bitmap bitmap = null;

		try {
			imgUrl = new URL("http://www.guju.com.cn/gimages/" + n
					+ "_0_9-.jpg");
			HttpURLConnection conn = (HttpURLConnection) imgUrl
					.openConnection();
			conn.connect();
			InputStream is = conn.getInputStream();
			BitmapFactory.Options ops = new BitmapFactory.Options();
			ops.inSampleSize = 3;
			bitmap = BitmapFactory.decodeStream(is, null, ops);
			SystemApplication.getInstance().addBitmapToMemoryCache(n, bitmap);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}

}
