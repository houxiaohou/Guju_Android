package cn.com.guju.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.ViewFlipper;
import cn.com.guju.R;
import cn.com.guju.Async.LoadImageTask;
import cn.com.guju.Async.RequestRunnable;
import cn.com.guju.listener.AddIdeaButton;
import cn.com.guju.listener.MyIdeaBookButton;
import cn.com.guju.listener.SpinnerListener;
import cn.com.guju.listener.SubmitButton;
import cn.com.guju.service.CategoryRequest;
import cn.com.guju.service.CheckNetInfo;
import cn.com.guju.service.JSONResolver;
import cn.com.guju.service.LoadLocalBitmap;
import cn.com.guju.service.SystemApplication;
import cn.com.guju.service.ToastLayout;

public class MainActivity extends Activity implements OnGestureListener {

	private static final String[] styles = { "全部风格", "地中海风情", "东方风韵", "现代主义",
			"当代风格", "传统格调", "折中主义", "热带风情" };
	private static final String[] spaces = { "全部空间", "浴室", "卧室", "更衣间", "餐厅",
			"玄关", "外景", "活动室", "走廊", "书房", "儿童房", "厨房", "园艺", "洗衣房", "客厅",
			"家庭影院", "庭院", "池", "阳台", "化妆间", "楼梯", "酒窖" };
	private GestureDetector gestureDetector = null;
	private Activity mActivity = null;
	private ImageView iv;
	private ViewFlipper viewFlipper;
	private SubmitButton buttonCtrl = null;
	private AddIdeaButton addIdeaButtonCtrl = null;
	private SpinnerListener spListener = null;
	private MyIdeaBookButton myIdeaButtonCtrl = null;
	private Spinner spaceSpinner;
	private Spinner styleSpinner;
	private ArrayAdapter<String> spaceAdapter;
	private ArrayAdapter<String> styleAdapter;
	private ProgressBar progressBar;

	private String styleId;
	private String spaceId;
	private LoadLocalBitmap loadLocal = new LoadLocalBitmap();
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
	
	private int o = 0;
	private int p = 0;
	private int set = 0;
	private int lp = 0;
	private int lo;

	private JSONObject jsonObj;
	private JSONResolver jsonResolver;
	private int availableResults = 2;

	private static final int MSG_SUCCESS = 0;
	private Bitmap bitmap;
	private Thread mThread;
	
	private CheckNetInfo checkNet;
	private boolean netStatus;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main);
		
		mActivity = this;
		iv = new ImageView(mActivity);
		viewFlipper = (ViewFlipper) findViewById(R.id.flipper);
		progressBar = (ProgressBar) findViewById(R.id.proBar);

		checkNet = new CheckNetInfo();
		netStatus = checkNet.checkNet(mActivity, iv, viewFlipper, 0);

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

		myIdeaButtonCtrl = new MyIdeaBookButton();
		myIdeaButtonCtrl.addMyIdeaButtonListener(mActivity, 0, iv, viewFlipper);

		spListener = new SpinnerListener(mActivity, styles, spaces, iv,
				viewFlipper, progressBar);
		spListener.clickListener();

		progressBar.setVisibility(View.VISIBLE);

		mThread = new Thread(runnable);
		mThread.start();

	}

	Runnable runnable = new Runnable() {

		public void run() {
			// TODO Auto-generated method stub
			Looper.prepare();
			try {
				request = new CategoryRequest();
				jsonResolver = new JSONResolver();
				jsonObj = request.request("0", "0", index);
				spaceIds = jsonResolver.getSpaceIds(jsonObj);
				int imageId = Integer.parseInt(spaceIds.get(n));
				bitmap = SystemApplication.getInstance()
						.getBitmapFromMemCache(imageId);
				if (bitmap != null) {
					mHandler.obtainMessage(MSG_SUCCESS, bitmap)
							.sendToTarget();
					Log.i("imageId", Integer.toString(imageId));
				} else {
					bitmap = new LoadImageTask().execute(imageId).get();
					mHandler.obtainMessage(MSG_SUCCESS, bitmap)
					.sendToTarget();
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	};

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_SUCCESS:
				iv = new ImageView(mActivity);
				iv.setImageBitmap((Bitmap) msg.obj);
				iv.setScaleType(ImageView.ScaleType.CENTER);
				viewFlipper.removeAllViews();	
				viewFlipper.addView(iv);
				break;
			}
		}
	};

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
			boolean styleStatus = SystemApplication.getInstance()
					.isStyleStatus();
			boolean spaceStatus = SystemApplication.getInstance()
					.isSpaceStatus();
			boolean isMyIdea = SystemApplication.getInstance()
					.isMyIdeaStatus();
			if (isMyIdea) {
				SystemApplication.getInstance().njian();
				int i = SystemApplication.getInstance().getValueOfn();
				loadLocal.loadLocalPic(mActivity, iv, viewFlipper, i);
				SystemApplication.getInstance().setStyleStatus(false);
				SystemApplication.getInstance().setSpaceStatus(false);
			} else if (netStatus) {
				SystemApplication.getInstance().njian();
				int i = SystemApplication.getInstance().getValueOfn();
				loadLocal.loadLocalPic(mActivity, iv, viewFlipper, i);
				SystemApplication.getInstance().setStyleStatus(false);
				SystemApplication.getInstance().setSpaceStatus(false);
			} else {
				// 没有选择分类，查看上一张
				if (styleStatus == false && spaceStatus == false) {
					if (x == 0) {
						new ToastLayout().showToast(mActivity, "前面没有了哦亲~");
					} else if (x > 0) {
						x--;
						int m = x % 10;
						int l = x / 10 % 10;
						if (l != lm) {
							index = index - 10;
						}
						viewFlipper.setOutAnimation(getApplicationContext(),R.anim.push_left_out);
						viewFlipper.removeView(iv);
						viewFlipper.addView(progressBar);
						Runnable runnable = new RequestRunnable("0", "0",
								index, m, mHandler);
						Thread thread = new Thread(runnable);
						thread.start();
						SystemApplication.getInstance().setBitmapId(
								spaceIds.get(m));

						lm = l;
						n = x;
					}
				}
				// 选择了一个分类，查看上一张
				else if ((styleStatus == true && spaceStatus == false)
						|| (styleStatus == false && spaceStatus == true)) {
					if (y == 0) {
						new ToastLayout().showToast(mActivity, "前面没有了哦亲~");
					} else if (y > 0) {
						y--;
						int m = y % 10;
						int l = y / 10 % 10;
						if (l != ln) {
							offset = offset - 10;
						}
						viewFlipper.removeView(iv);
						viewFlipper.addView(progressBar);
						Runnable runnable = new RequestRunnable(styleId,
								spaceId, offset, m, mHandler);
						Thread thread = new Thread(runnable);
						thread.start();
						SystemApplication.getInstance().setBitmapId(
								spaceIds.get(m));

						ln = l;
						k = y;
					}
				}
				// 选择了两个分类，查看上一张
				else if (styleStatus && spaceStatus) {
					if (p == 0) {
						new ToastLayout().showToast(mActivity, "前面没有了哦亲~");
					} else if (p > 0) {
						p--;
						int m = p % 10;
						int l = p / 10 % 10;
						if (l != lo) {
							set = set - 10;
						}
						viewFlipper.removeView(iv);
						viewFlipper.addView(progressBar);
						Runnable runnable = new RequestRunnable(styleId,
								spaceId, set, m, mHandler);
						Thread thread = new Thread(runnable);
						thread.start();
						SystemApplication.getInstance().setBitmapId(
								spaceIds.get(m));

						lo = l;
						o = p;
					}
				}
			}
		} else if (e2.getX() - e1.getX() < -80) {
			boolean styleStatus = SystemApplication.getInstance()
					.isStyleStatus();
			boolean spaceStatus = SystemApplication.getInstance()
					.isSpaceStatus();
			boolean isMyIdea = SystemApplication.getInstance()
					.isMyIdeaStatus();
			if (isMyIdea) {
				SystemApplication.getInstance().njia();
				int i = SystemApplication.getInstance().getValueOfn();
				loadLocal.loadLocalPic(mActivity, iv, viewFlipper, i);
				SystemApplication.getInstance().setStyleStatus(false);
				SystemApplication.getInstance().setSpaceStatus(false);
			} else if (netStatus) {
				SystemApplication.getInstance().njia();
				int i = SystemApplication.getInstance().getValueOfn();
				loadLocal.loadLocalPic(mActivity, iv, viewFlipper, i);
				SystemApplication.getInstance().setStyleStatus(false);
				SystemApplication.getInstance().setSpaceStatus(false);
			} else {
				// 没有选择分类，查看下一张
				if (styleStatus == false && spaceStatus == false) {
					n++;
					int m = n % 10;
					int l = n / 10 % 10;
					if (l != lx) {
						index = index + 10;
					}
					
					viewFlipper.removeAllViews();
					viewFlipper.addView(progressBar);
					Runnable runnable = new RequestRunnable("0", "0", index, m,
							mHandler);
					Thread thread = new Thread(runnable);
					thread.start();
					SystemApplication.getInstance()
							.setBitmapId(spaceIds.get(m));
					x = n;
					lx = l;
					lm = l;

				}
				// 选择了一个分类，查看下一张
				else if ((styleStatus == true && spaceStatus == false)
						|| (styleStatus == false && spaceStatus == true)) {
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
							spinnerInfo = spListener.clickListener();
							styleId = spinnerInfo.get("styleId");
							spaceId = spinnerInfo.get("spaceId");

							viewFlipper.removeView(iv);
							viewFlipper.addView(progressBar);
							Runnable runnable = new RequestRunnable(styleId,
									spaceId, offset, m, mHandler);
							Thread thread = new Thread(runnable);
							thread.start();
							SystemApplication.getInstance().setBitmapId(
									spaceIds.get(m));

							jsonResolver = new JSONResolver();
							jsonObj = request.request(styleId, spaceId, offset);
							availableResults = jsonResolver
									.getAvailableResults(jsonObj);

						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						new ToastLayout().showToast(mActivity, "最后一张了哦~");
					}
				} 
				//选择了两个分类，查看下一张
				else if (styleStatus && spaceStatus) {
					o++;
					if (o <= availableResults - 1) {
						int m = o % 10;
						int l = o / 10 % 10;
						if (l != lp) {
							set = set + 10;
						}
						p = o;
						lp = l;
						lo = l;

						try {
							spinnerInfo = spListener.clickListener();
							styleId = spinnerInfo.get("styleId");
							spaceId = spinnerInfo.get("spaceId");

							viewFlipper.removeView(iv);
							viewFlipper.addView(progressBar);
							Runnable runnable = new RequestRunnable(styleId,
									spaceId, set, m, mHandler);
							Thread thread = new Thread(runnable);
							thread.start();
							SystemApplication.getInstance().setBitmapId(
									spaceIds.get(m));

							jsonResolver = new JSONResolver();
							jsonObj = request.request(styleId, spaceId, set);
							availableResults = jsonResolver
									.getAvailableResults(jsonObj);

						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						new ToastLayout().showToast(mActivity, "最后一张了哦~");
					}
				}
			}
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
					System.exit(0);
				}

			}).show();

			return true;

		} else {

			return super.onKeyDown(keyCode, event);

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);// 指定使用的XML
		return true;
	}

	/* 处理菜单事件 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int item_id = item.getItemId();// 得到当前选中MenuItem的ID
		switch (item_id) {
		case R.id.about: {
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), AboutActivity.class);
			startActivity(intent);
			finish();
		}
		case R.id.exit: {
			System.exit(0);
		}
		}
		return true;
	}

}