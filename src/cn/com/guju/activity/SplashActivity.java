package cn.com.guju.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import cn.com.guju.R;

public class SplashActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.splash);
		Handler x = new Handler();
		x.postDelayed(new splashhandler(), 3000);

	}

	class splashhandler implements Runnable {

		public void run() {
			SplashActivity.this.finish();
			startActivity(new Intent(getApplication(), MainActivity.class));
			
		}

	}
	
}
