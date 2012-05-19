package android.guju.activity;

import android.app.Activity;
import android.content.Intent;
import android.guju.R;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class AboutActivity extends Activity{
	
	private ImageView website;
	private ImageView weibo;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.about);
		
		website = (ImageView) findViewById(R.id.website);
		weibo = (ImageView) findViewById(R.id.weibo);
		
		website.setOnClickListener(new ImageView.OnClickListener(){

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();  
	            intent.setAction(Intent.ACTION_VIEW);  
	            intent.addCategory(Intent.CATEGORY_BROWSABLE);  
	            intent.setData(Uri.parse("http://www.guju.com.cn"));  
	            startActivity(intent);
			}
			
		});
		
		weibo.setOnClickListener(new ImageView.OnClickListener(){

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();  
	            intent.setAction(Intent.ACTION_VIEW);  
	            intent.addCategory(Intent.CATEGORY_BROWSABLE);  
	            intent.setData(Uri.parse("http://weibo.com/loveguju"));  
	            startActivity(intent);
			}
			
		});
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), MainActivity.class);
			startActivity(intent);
			finish();
			return true;
		} else {

			return super.onKeyDown(keyCode, event);

		}

	}

}
