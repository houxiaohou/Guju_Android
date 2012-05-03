package android.guju.listener;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.guju.R;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

public class RegisterButton {
	

	private Button registerButton;
	private SubmitButton submitButton = null;
	
	public void addButtonControl(final Activity activity){
		
		registerButton = (Button) activity.findViewById(R.id.register);
		
		registerButton.setOnClickListener(new Button.OnClickListener(){
			
			

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				LayoutInflater factory = LayoutInflater.from(activity);
				final View regView = factory.inflate(R.layout.submit, null);
				
				AlertDialog subDialog = new AlertDialog.Builder(activity)
					.setTitle("用户登录")
					.setView(regView)
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
									submitButton = new SubmitButton();
									submitButton.submitListener(activity);
								}
						
					}).create();
				subDialog.show();
			}
			
		});

	}
	
}
