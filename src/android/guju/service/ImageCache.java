package android.guju.service;

import java.util.LinkedHashMap;

import android.graphics.Bitmap;

public class ImageCache {
	
	private static ImageCache cache;
	private LinkedHashMap<Integer, Bitmap> softCache;
	
	public ImageCache(){
		softCache = new LinkedHashMap<Integer, Bitmap>();
	}
	
	
	public static ImageCache getInstance() {
	    if (cache == null) {
	        cache = new ImageCache();
	    }
	    return cache;
	}
	
	public void addBitmapToCache(int key, Bitmap bitmap){
		cleanCache(key-2);
		cleanCache(key+2);
		if(getBitmapCache(key) == null){
			softCache.put(key, bitmap);
		}
	}
	
	public Bitmap getBitmapCache(int key){
		return softCache.get(key);
	}
	
	public void cleanCache(int n){
		if(softCache.containsKey(n)){
			softCache.remove(n);
		}
		if(softCache.get(n) != null && !softCache.get(n).isRecycled()){
			softCache.get(n).recycle();
		}
	}
	
}