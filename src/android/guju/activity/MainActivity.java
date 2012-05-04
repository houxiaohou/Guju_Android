package android.guju.activity;

import java.util.Random;

import android.app.Activity;
import android.graphics.Bitmap;
import android.guju.R;
import android.guju.listener.AddIdeaButton;
import android.guju.listener.SubmitButton;
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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.ViewFlipper;

public class MainActivity extends Activity implements OnGestureListener {

	private GestureDetector gestureDetector = null;
	private Activity mActivity = null;
	private int num = randomInt();
	private ImageView iv;
	private ViewFlipper viewFlipper;
	private SubmitButton buttonCtrl = null;
	private AddIdeaButton addIdeaButtonCtrl = null;
	private ImageCache cache = ImageCache.getInstance();
	private static final String[] styles = { "全部风格", "地中海式", "亚洲风", "现代风格",
			"当代风格", "传统风格", "不拘一格", "热带风格" };
	private Spinner styleSpinner;
	private ArrayAdapter<String> styleAdapter;
	private static final String[] spaces = { "全部空间", "浴室", "卧室", "壁橱", "餐厅",
			"门厅", "外观", "客厅", "大堂", "办公间", "儿童房", "厨房", "景观", "洗衣房", "起居室",
			"媒体室", "天井", "池", "门廊", "化妆间", "楼梯", "酒窖" };
	private Spinner spaceSpinner;
	private ArrayAdapter<String> spaceAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main);
		
		mActivity = this;
		viewFlipper = (ViewFlipper) findViewById(R.id.flipper);
		styleSpinner = (Spinner) findViewById(R.id.style);
		styleAdapter = new ArrayAdapter<String>(this, R.layout.stylespinner,
				styles);
		styleSpinner.setAdapter(styleAdapter);

		spaceSpinner = (Spinner) findViewById(R.id.space);
		spaceAdapter = new ArrayAdapter<String>(this, R.layout.spacespinner,
				spaces);
		spaceSpinner.setAdapter(spaceAdapter);

		gestureDetector = new GestureDetector(this);
		
		buttonCtrl = new SubmitButton();
		buttonCtrl.submitListener(mActivity);
		
		addIdeaButtonCtrl = new AddIdeaButton();
		addIdeaButtonCtrl.addIdeaButtonListener(mActivity);
		
		loadImage(num, viewFlipper);
	}

	public int randomInt() {
		int num = (Math.abs(new Random().nextInt())) % 5041 + 1;
		return num;
	}

	public void loadImage(int n, ViewFlipper viewFlipper) {
		final Bitmap bitmap = cache.getBitmapCache(n);
		iv = new ImageView(this);
		if (bitmap != null) {
			iv.setImageBitmap(bitmap);
			iv.setScaleType(ImageView.ScaleType.CENTER);
			viewFlipper.removeView(viewFlipper);
			viewFlipper.addView(iv);
		} else {
			LoadImageTask task = new LoadImageTask(iv);
			task.execute(n);
			iv.setImageBitmap(bitmap);
			iv.setScaleType(ImageView.ScaleType.CENTER);
			viewFlipper.removeView(viewFlipper);
			viewFlipper.addView(iv);
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

			viewFlipper.setAnimation(rInAnim);
			viewFlipper.setAnimation(rOutAnim);
			viewFlipper.showPrevious();
			num = num - 1;
			loadImage(num, viewFlipper);
			return true;
		} else if (e2.getX() - e1.getX() < -80) {
			Animation lInAnim = AnimationUtils.loadAnimation(mActivity,
					R.anim.push_left_in);
			Animation lOutAnim = AnimationUtils.loadAnimation(mActivity,
					R.anim.push_left_out);

			viewFlipper.startAnimation(lInAnim);
			viewFlipper.startAnimation(lOutAnim);
			viewFlipper.showNext();
			num = num + 1;
			loadImage(num, viewFlipper);
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