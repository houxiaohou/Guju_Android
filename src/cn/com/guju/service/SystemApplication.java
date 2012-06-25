package cn.com.guju.service;

import android.app.Application;
import android.graphics.Bitmap;

public class SystemApplication extends Application{
	
	private boolean spaceStatus;
	private boolean styleStatus;
	private boolean myIdeaStatus;
	private LruCache<Integer,Bitmap> mMemoryCache;
	private String cBitmapId;
	private int n = 0;
	
	private static SystemApplication singleton;
	
	public static SystemApplication getInstance(){
		return singleton;
	}
	
	public boolean isSpaceStatus() {
		return spaceStatus;
	}

	public void setSpaceStatus(boolean spaceStatus) {
		this.spaceStatus = spaceStatus;
	}

	public boolean isStyleStatus() {
		return styleStatus;
	}



	public void setStyleStatus(boolean styleStatus) {
		this.styleStatus = styleStatus;
	}
	
	public boolean isMyIdeaStatus() {
		return myIdeaStatus;
	}

	public void setMyIdeaStatus(boolean myIdeaStatus) {
		this.myIdeaStatus = myIdeaStatus;
	}

	public int getValueOfn(){
		return n;
	}
	
	public void setValueOfn(int m){
		n = m;
	}
	
	public int njia(){
		n = n + 1;
		return n;
	}
	
	public int njian(){
		n = n - 1;
		return n;
	}
	
	@Override
	public void onCreate(){
		super.onCreate();
		singleton = this;
		setStyleStatus(false);
		setSpaceStatus(false);
		setMyIdeaStatus(false);
	    mMemoryCache = new LruCache<Integer,Bitmap>(10);
	}
	
	public void addBitmapToMemoryCache(int key, Bitmap bitmap) {
	    if (getBitmapFromMemCache(key) == null) {  
	        mMemoryCache.put(key, bitmap);  
	    }  
	}  
	  
	public Bitmap getBitmapFromMemCache(int key) {  
	    return mMemoryCache.get(key);  
	}
	
	public void setBitmapId(String i){
		cBitmapId = i;
	}
	
	public String getBitmapId(){
		return cBitmapId;
	}
}
