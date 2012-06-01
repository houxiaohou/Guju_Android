package android.guju.service;

import android.app.Application;
import android.graphics.Bitmap;

public class SystemApplication extends Application{
	
	private boolean space;
	private boolean style;
	private boolean isMyIdea;
	private LruCache<Integer,Bitmap> mMemoryCache;
	private String cBitmapId;
	private int n = 0;
	
	private static SystemApplication singleton;
	
	public static SystemApplication getInstance(){
		return singleton;
	}
	
	public void setSpaceStatus(boolean b){
		space = b;
	}
	
	public boolean getSpaceStatus(){
		return space;
	}
	
	public void setStyleStatus(boolean b){
		style = b;
	}
	
	public boolean getStyleStatus(){
		return style;
	}
	
	public void setMyIdeaStatus(boolean b){
		isMyIdea = b;
	}
	
	public boolean getMyIdeaStatus(){
		return isMyIdea;
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
		n = n -1;
		return n;
	}
	
	@Override
	public void onCreate(){
		super.onCreate();
		singleton = this;
		setStyleStatus(false);
		setSpaceStatus(false);
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
