package android.guju.Async;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.guju.service.SystemApplication;
import android.os.AsyncTask;

public class AsyncLoadTask extends AsyncTask<Integer, Integer, Bitmap> {

	private int n;

	public AsyncLoadTask(int n) {
		this.n = n;
	}

	@Override
	protected void onPreExecute() {

	}

	@Override
	protected void onPostExecute(Bitmap bitmap) {

	}

	@Override
	protected Bitmap doInBackground(Integer... params) {
		// TODO Auto-generated method stub
		URL imgUrl = null;
		Bitmap bitmap = null;
		bitmap = SystemApplication.getInstance().getBitmapFromMemCache(n);
		if (bitmap != null) {
			return bitmap;
		} else {
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
				SystemApplication.getInstance().addBitmapToMemoryCache(n,
						bitmap);
				is.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return bitmap;
		}
	}

}
