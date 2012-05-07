package android.guju.activity;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.guju.R;
import android.guju.listener.AddIdeaButton;
import android.guju.listener.SubmitButton;
import android.guju.service.ImageCache;
import android.guju.service.LoadImage;
import android.guju.service.SystemConstant;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.ViewFlipper;

public class MainActivity extends Activity implements OnGestureListener {

	private GestureDetector gestureDetector = null;
	private Activity mActivity = null;
	private ImageView iv;
	private ViewFlipper viewFlipper;
	private SubmitButton buttonCtrl = null;
	private AddIdeaButton addIdeaButtonCtrl = null;
	private ImageCache cache = ImageCache.getInstance();

	private static final String[] styles = { "全部风格", "地中海式", "亚洲风", "现代风格",
			"当代风格", "传统风格", "不拘一格", "热带风格" };
	private static final String[] spaces = { "全部空间", "浴室", "卧室", "壁橱", "餐厅",
			"门厅", "外观", "客厅", "大堂", "办公间", "儿童房", "厨房", "景观", "洗衣房", "起居室",
			"媒体室", "天井", "池", "门廊", "化妆间", "楼梯", "酒窖" };
	private Spinner spaceSpinner;
	private Spinner styleSpinner;
	private ArrayAdapter<String> spaceAdapter;
	private ArrayAdapter<String> styleAdapter;
	private ArrayList<String> spaceIds;
	private int offset = randomInt();
	private int n = 0;
	private String styleId;
	private String spaceId;
	private LoadImage loadImage = new LoadImage();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main);

		mActivity = this;
		viewFlipper = (ViewFlipper) findViewById(R.id.flipper);

		// styleSpinner设置listener
		styleSpinner = (Spinner) findViewById(R.id.style);
		styleAdapter = new ArrayAdapter<String>(this, R.layout.stylespinner,
				styles);
		styleSpinner.setAdapter(styleAdapter);
		styleSpinner
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						String style = styles[arg2];
						styleId = SystemConstant.STYLES_ID_MAP.get(style);
					}

					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
					}
				});

		// spaceSpinner 设置listener
		spaceSpinner = (Spinner) findViewById(R.id.space);
		spaceAdapter = new ArrayAdapter<String>(this, R.layout.spacespinner,
				spaces);
		spaceSpinner.setAdapter(spaceAdapter);
		spaceSpinner
				.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						String space = spaces[arg2];
						spaceId = SystemConstant.CATEGORIES_ID_MAP.get(space);
					}

					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
					}
				});

		gestureDetector = new GestureDetector(this);

		buttonCtrl = new SubmitButton();
		buttonCtrl.submitListener(mActivity);

		addIdeaButtonCtrl = new AddIdeaButton();
		addIdeaButtonCtrl.addIdeaButtonListener(mActivity);

		try {
			loadImage.loadImage(n, iv, viewFlipper, mActivity, offset,
					spaceIds, cache, styleId, spaceId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public int randomInt() {
		int num = (Math.abs(new Random().nextInt())) % 6521 + 1;
		return num;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return gestureDetector.onTouchEvent(event);
	}

	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		if (e2.getX() - e1.getX() > 80) {

			if (styleId.equals("0") && spaceId.equals("0")) {
				if (1 <= n && n <= 9) {
					n = n - 1;
					try {
						loadImage.loadImage(n, iv, viewFlipper, mActivity,
								offset, spaceIds, cache, styleId, spaceId);
						viewFlipper.showPrevious();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if (n == 0) {
					offset = offset - 10;
					n = 9;
					try {
						loadImage.loadImage(n, iv, viewFlipper, mActivity,
								offset, spaceIds, cache, styleId, spaceId);
						viewFlipper.showPrevious();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} else {
				offset = 0;

				try {
					loadImage.loadImage(n, iv, viewFlipper, mActivity, offset,
							spaceIds, cache, styleId, spaceId);
					viewFlipper.showPrevious();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return true;
		} else if (e2.getX() - e1.getX() < -80) {
			if (styleId.equals("0") && spaceId.equals("0")) {
				if (1 <= n && n <= 9) {
					n = n + 1;
					try {
						loadImage.loadImage(n, iv, viewFlipper, mActivity,
								offset, spaceIds, cache, styleId, spaceId);
						viewFlipper.showNext();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if (n > 9) {
					offset = offset + 10;
					n = 0;
					try {
						loadImage.loadImage(n, iv, viewFlipper, mActivity,
								offset, spaceIds, cache, styleId, spaceId);
						viewFlipper.showNext();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} else {
				offset = 0;

				try {
					loadImage.loadImage(n, iv, viewFlipper, mActivity, offset,
							spaceIds, cache, styleId, spaceId);
					viewFlipper.showNext();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

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