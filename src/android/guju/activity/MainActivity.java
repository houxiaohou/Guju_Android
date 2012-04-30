package android.guju.activity;

import java.util.Random;

import android.app.Activity;
import android.graphics.Bitmap;
import android.guju.R;
import android.guju.listener.ButtonControl;
import android.guju.service.ImageCache;
import android.guju.service.LoadImageTask;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class MainActivity extends Activity implements OnGestureListener {

	private GestureDetector gestureDetector = null;
	private ViewFlipper viewFlipper = null;
	private Activity mActivity = null;
	private int num = randomInt();
	private ImageView iv;
	private ButtonControl buttonCtrl = null;
	ImageCache  cache = ImageCache.getInstance();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main);
		
		mActivity = this;
		viewFlipper = (ViewFlipper) findViewById(R.id.flipper);
		gestureDetector = new GestureDetector(this);
		buttonCtrl = new ButtonControl();
		buttonCtrl.addButtonControl(mActivity);
		
		iv = new ImageView(this);
		loadImage(num, iv, viewFlipper);
	}

	public int randomInt() {
		int num = (Math.abs(new Random().nextInt())) % 5041 + 1;
		return num;
	}
	
	public void loadImage(int n,ImageView imageView, ViewFlipper viewFlipper){
        final Bitmap bitmap = cache.getBitmapCache(n);
        if(bitmap != null){
        	imageView.setImageBitmap(bitmap);
        	imageView.setScaleType(ImageView.ScaleType.CENTER);
        	viewFlipper.removeView(imageView);
    		viewFlipper.addView(imageView);
        }else{
        	LoadImageTask task = new LoadImageTask(imageView);
        	task.execute(n+1);
        	imageView.setScaleType(ImageView.ScaleType.CENTER);
        	viewFlipper.removeView(imageView);
        	viewFlipper.addView(imageView);
        	
        }
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return gestureDetector.onTouchEvent(event);
	}
	
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		if (e2.getX() - e1.getX() > 80) {
			Animation rInAnim = AnimationUtils.loadAnimation(mActivity,
					R.anim.push_right_in);
			Animation rOutAnim = AnimationUtils.loadAnimation(mActivity,
					R.anim.push_right_out);
			num = num - 1;
			loadImage(num, iv, viewFlipper);
			viewFlipper.setInAnimation(rInAnim);
			viewFlipper.setOutAnimation(rOutAnim);
			viewFlipper.showPrevious();
			return true;
		} else if (e2.getX() - e1.getX() < -80) {
			Animation lInAnim = AnimationUtils.loadAnimation(mActivity,
					R.anim.push_left_in);
			Animation lOutAnim = AnimationUtils.loadAnimation(mActivity,
					R.anim.push_left_out);
			num = num + 1;
			loadImage(num, iv, viewFlipper);
			viewFlipper.setInAnimation(lInAnim);
			viewFlipper.setOutAnimation(lOutAnim);
			viewFlipper.showNext();
			return true;
		}
		return true;
	}

	public boolean onDown(MotionEvent e) {
		return false;
	}

	public void onLongPress(MotionEvent e) {
	}

	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		return false;
	}

	public void onShowPress(MotionEvent e) {
	}

	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}
}