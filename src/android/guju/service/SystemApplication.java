package android.guju.service;

import android.app.Application;
import android.graphics.Bitmap;

public class SystemApplication extends Application{
	
	private boolean isPressed ;
	private LruCache<Integer,Bitmap> mMemoryCache;
	private String cBitmapId;
	
	private static SystemApplication singleton;
	
	public static SystemApplication getInstance(){
		return singleton;
	}
	
	public void setStatus(boolean b){
		isPressed = b;
	}
	
	public boolean getStatus(){
		return isPressed;
	}
	
	@Override
	public void onCreate(){
		super.onCreate();
		singleton = this;
		setStatus(false);
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
