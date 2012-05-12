package android.guju.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.guju.R;
import android.guju.listener.AddIdeaButton;
import android.guju.listener.CateConfirmButton;
import android.guju.listener.SubmitButton;
import android.guju.service.CategoryRequest;
import android.guju.service.JSONResolver;
import android.guju.service.LoadImage;
import android.guju.service.SystemApplication;
import android.guju.service.ToastLayout;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.ViewFlipper;

public class MainActivity extends Activity implements OnGestureListener {

	private static final String[] styles = { "全部风格", "地中海式", "日韩亚洲", "现代简约",
			"当代美学", "复古传统", "折衷主义", "热带风情" };
	private static final String[] spaces = { "全部空间", "浴室", "卧室", "壁橱", "餐厅",
			"玄关", "室外景观", "客厅", "大厅", "书房", "儿童房", "厨房", "景观", "洗衣间", "起居室",
			"媒体室", "露台", "游泳池", "走廊", "化妆间", "楼梯", "酒架" };
	private GestureDetector gestureDetector = null;
	private Activity mActivity = null;
	private ImageView iv;
	private ViewFlipper viewFlipper;
	private SubmitButton buttonCtrl = null;
	private AddIdeaButton addIdeaButtonCtrl = null;
	private CateConfirmButton cateConfirmButt = null;
	private Spinner spaceSpinner;
	private Spinner styleSpinner;
	private ArrayAdapter<String> spaceAdapter;
	private ArrayAdapter<String> styleAdapter;

	private String styleId;
	private String spaceId;
	private LoadImage loadImage = new LoadImage();
	private HashMap<String, String> spinnerInfo;
	private CategoryRequest request;
	private ArrayList<String> spaceIds;

	private int n = 0;
	private int x = 0;
	private int index = randomInt();
	private int lx;
	private int lm;

	private int k = 0;
	private int y = 0;
	private int offset = 0;
	private int ly = 0;
	private int ln;

	private JSONObject jsonObj;
	private JSONResolver jsonResolver;
	private int availableResults = 2;

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
		styleAdapter = new ArrayAdapter<String>(this, R.layout.spinnerselected,
				styles);
		styleAdapter.setDropDownViewResource(R.layout.spinner);
		styleSpinner.setAdapter(styleAdapter);

		spaceSpinner = (Spinner) findViewById(R.id.space);
		spaceAdapter = new ArrayAdapter<String>(this, R.layout.spinnerselected,
				spaces);
		spaceAdapter.setDropDownViewResource(R.layout.spinner);
		spaceSpinner.setAdapter(spaceAdapter);

		gestureDetector = new GestureDetector(this);

		buttonCtrl = new SubmitButton();
		buttonCtrl.submitListener(mActivity);

		addIdeaButtonCtrl = new AddIdeaButton();
		addIdeaButtonCtrl.addIdeaButtonListener(mActivity);

		cateConfirmButt = new CateConfirmButton();
		try {
			cateConfirmButt.addCateButtonListener(mActivity, styles, spaces,
					iv, viewFlipper);

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			request = new CategoryRequest();
			jsonResolver = new JSONResolver();
			jsonObj = request.request("0", "0", index);
			spaceIds = jsonResolver.getSpaceIds(jsonObj);
			loadImage.loadImage(0, iv, viewFlipper, mActivity, spaceIds);
			SystemApplication.getInstance().setBitmapId(spaceIds.get(0));
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
	public void onDetachedFromWindow() {
		try {
			super.onDetachedFromWindow();
		} catch (IllegalArgumentException e) {
			
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return gestureDetector.onTouchEvent(event);
	}

	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		if (e2.getX() - e1.getX() > 80) {
			boolean status = SystemApplication.getInstance().getStatus();
			// 没有选择分类，查看上一张
			if (!status) {
				if (x == 0) {
					new ToastLayout().showToast(mActivity, "前面没有了哦亲~");
				} else if (x > 0) {
					x--;
					int m = x % 10;
					int l = x / 10 % 10;
					if (l != lm) {
						index = index - 10;
						try {
							request = new CategoryRequest();
							jsonResolver = new JSONResolver();
							jsonObj = request.request("0", "0", index);
							spaceIds = jsonResolver.getSpaceIds(jsonObj);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					lm = l;
					n = x;
					try {
						loadImage.loadImage(m, iv, viewFlipper, mActivity,
								spaceIds);
						SystemApplication.getInstance().setBitmapId(
								spaceIds.get(m));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			// 选择了分类，查看上一张
			else {
				if (y == 0) {
					new ToastLayout().showToast(mActivity, "前面没有了哦亲~");
				} else if (y > 0) {
					y--;
					int m = y % 10;
					int l = y / 10 % 10;
					if (l != ln) {
						try {
							offset = offset - 10;
							spinnerInfo = cateConfirmButt.getSpinnerInfo(
									mActivity, styles, spaces);
							styleId = spinnerInfo.get("styleId");
							spaceId = spinnerInfo.get("spaceId");
							request = new CategoryRequest();
							jsonResolver = new JSONResolver();
							jsonObj = request.request(styleId, spaceId, offset);
							spaceIds = jsonResolver.getSpaceIds(jsonObj);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					ln = l;
					k = y;
					try {
						loadImage.loadImage(m, iv, viewFlipper, mActivity,
								spaceIds);
						SystemApplication.getInstance().setBitmapId(
								spaceIds.get(m));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			return true;
		} else if (e2.getX() - e1.getX() < -80) {
			boolean status = SystemApplication.getInstance().getStatus();
			// 没有选择分类，查看下一张
			if (!status) {
				n++;
				int m = n % 10;
				int l = n / 10 % 10;
				if (l != lx) {
					index = index + 10;
					request = new CategoryRequest();
					try {
						jsonResolver = new JSONResolver();
						jsonObj = request.request("0", "0", index);
						spaceIds = jsonResolver.getSpaceIds(jsonObj);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				x = n;
				lx = l;
				lm = l;
				try {
					loadImage
							.loadImage(m, iv, viewFlipper, mActivity, spaceIds);
					SystemApplication.getInstance()
							.setBitmapId(spaceIds.get(m));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// 选择了分类，查看下一张
			else {
				k++;
				if (k <= availableResults - 1) {
					int m = k % 10;
					int l = k / 10 % 10;
					if (l != ly) {
						offset = offset + 10;
					}
					y = k;
					ly = l;
					ln = l;
					try {
						spinnerInfo = cateConfirmButt.getSpinnerInfo(mActivity,
								styles, spaces);
						styleId = spinnerInfo.get("styleId");
						spaceId = spinnerInfo.get("spaceId");
						request = new CategoryRequest();
						jsonResolver = new JSONResolver();
						jsonObj = request.request(styleId, spaceId, offset);
						availableResults = jsonResolver
								.getAvailableResults(jsonObj);
						spaceIds = jsonResolver.getSpaceIds(jsonObj);
						loadImage.loadImage(m, iv, viewFlipper, mActivity,
								spaceIds);
						SystemApplication.getInstance().setBitmapId(
								spaceIds.get(m));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					new ToastLayout().showToast(mActivity, "最后一张了哦~");
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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {

			new AlertDialog.Builder(this)

			.setTitle("提示")

			.setMessage("确认退出？")

			.setNegativeButton("再看一会", new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub

				}
			})

			.setPositiveButton("我要退出", new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int whichButton) {

					finish();

				}

			}).show();

			return true;

		} else {

			return super.onKeyDown(keyCode, event);

		}

	}

}