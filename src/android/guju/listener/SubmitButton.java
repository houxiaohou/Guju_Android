package android.guju.listener;

import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.guju.R;
import android.guju.action.registerAction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

public class SubmitButton {
	
	private Button registerButton;
	private registerAction regAction;
	
	public void addButtonControl(final Activity activity){
		
		registerButton = (Button) activity.findViewById(R.id.register);
		
		registerButton.setOnClickListener(new Button.OnClickListener(){
			
			

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				LayoutInflater factory = LayoutInflater.from(activity);
				final View DialogView = factory.inflate(R.layout.submit, null);
				
				AlertDialog subDialog = new AlertDialog.Builder(activity)
					.setTitle("用户登录")
					.setView(DialogView)
					.setPositiveButton("提交登录", 
							new OnClickListener(){

							
								public void onClick(DialogInterface arg0,
										int arg1) {
									// TODO Auto-generated method stub
									
								}
						
					})
					.setNegativeButton("没有账号",
							new OnClickListener(){

								
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									LayoutInflater factory = LayoutInflater.from(activity);
									final View DialogView = factory.inflate(R.layout.register, null);
									
									AlertDialog regDialog = new AlertDialog.Builder(activity)
										.setTitle("用户注册")
										.setView(DialogView)
										.setPositiveButton("提交", 
												new OnClickListener(){

												
													public void onClick(DialogInterface arg0,
															int arg1) {
														// TODO Auto-generated method stub
														
														try {
															regAction = new registerAction();
															regAction.getUserInfo(activity);
														} catch (IOException e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														} catch (Exception e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														}
													}
											
										})
										.setNegativeButton("取消",
												new OnClickListener(){

											
													public void onClick(DialogInterface dialog,
															int which) {
														// TODO Auto-generated method stub
														dialog.dismiss();
													}
											
										}).create();
									regDialog.show();
								}
						
					}).create();
				subDialog.show();
			}
			
		});

	}
	
}
