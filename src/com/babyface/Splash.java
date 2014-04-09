package com.babyface;

import com.example.babyface.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

public class Splash extends Activity
{

	@Override
	protected void onCreate(Bundle babyFaceSPLASH)
	{
		// TODO Auto-generated method stub
		super.onCreate(babyFaceSPLASH);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash);
		Thread timer = new Thread(){
			public void run(){
				try{
					sleep(4500);
					
				}catch(InterruptedException exc){
					exc.printStackTrace();
				}finally{
					Intent startPoint = new Intent("com.example.babyface.MAINACTIVITY");
					startActivity(startPoint);
				}
			}
		};
		timer.start();
	}

	@Override
	protected void onPause() 
	{
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}

}
