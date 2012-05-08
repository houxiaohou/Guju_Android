package android.guju.service;

import android.app.Application;

public class ButtonStatus extends Application{
	
	private boolean isPressed ;
	
	private static ButtonStatus singleton;
	
	public static ButtonStatus getInstance(){
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
	}
}
