package android.guju.service;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

public class LoadImageTask extends AsyncTask<Integer, Integer, Bitmap> {

	@Override
	protected void onPreExecute() {
		
	}
	
	@Override  
    protected void onProgressUpdate(Integer... values) {   
		
    }
	
	@Override
	protected Bitmap doInBackground(Integer... m) {
		return getBitmap(m[0]);
	}

	@Override
	protected void onPostExecute(Bitmap bitmap) {
		
	}

	public Bitmap getBitmap(int n) {
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
