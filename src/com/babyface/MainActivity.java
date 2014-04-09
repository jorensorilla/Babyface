package com.babyface;

import com.babyface.model.DiaryModel;
import com.example.babyface.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;


public class MainActivity extends Activity {
	
	Button exit;
	DiaryModel diary;

	GridView gridView;
	ImageAdapter adapter;
	AboutUsDialog aboutus;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_main);
		 gridView = (GridView) findViewById(R.id.grid_view);
		
		 
	     // Instance of ImageAdapter Class
		 aboutus = new AboutUsDialog(MainActivity.this, true, new OnCancelListener(){

				@Override
				public void onCancel(DialogInterface dialog) {
					aboutus.dismiss();
					
				}
 			
 		 });
		
		 aboutus.requestWindowFeature(Window.FEATURE_NO_TITLE);
		 aboutus.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		 diary = new DiaryModel(BabyFaceDAO.getInstance(getBaseContext()));
		 adapter = new ImageAdapter(this, diary.getAllDiary());
	     gridView.setAdapter(adapter);
			 
	}
	
	public void onResume(){
		super.onResume();
		adapter.setDiaries(diary.getAllDiary());
		adapter.notifyDataSetChanged();
		//gridView.setAdapter(adapter);
	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	    case R.id.add_view:
	    	Intent i = new Intent(this, AddDiaryActivity.class);
	    	startActivity(i);
	        return true;
	    case R.id.about_view:
	    	if(!aboutus.isShowing()){
	    		aboutus.show();
	    	}
	        return false;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		getMenuInflater().inflate(R.menu.main_menu_actions, menu);
		return true;
	}
	
//	

}
