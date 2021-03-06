package cn.com.guju.Async;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.com.guju.service.SystemApplication;
import cn.com.guju.service.SystemConstant;

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
			imgUrl = new URL(SystemConstant.BASE_URL+SystemConstant.SIMAGES + n
					+ SystemConstant.PHOTO_VERSION);
			HttpURLConnection conn = (HttpURLConnection) imgUrl
					.openConnection();
			conn.connect();
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
			SystemApplication.getInstance().addBitmapToMemoryCache(n, bitmap);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}

}
