package com.babyface;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.babyface.model.EventModel;
import com.babyface.object.Event;
import com.example.babyface.R;

public class AddEntry extends Activity  {
	 
	  private static final int PICK_FROM_CAMERA = 0;
	  private MyHorizontalLayout myHorizontalLayout;
	  private ArrayList<String> path;
	  private BabyFaceDAO db;
	  private EventModel eventModel;
	  private Event event = new Event();
	  private TextView eventName;
	  private TextView eventDesc;
	  private Button from_camera;
	  private Button from_gallery;
	  private int diary_id;
	  
	  File output;
	  private String finalImagePath;
	  private Uri selectedImageUri;
	  private boolean isImageSelected;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_ACTION_BAR);
		setContentView(R.layout.activity_add_entry);
		Bundle b = getIntent().getExtras();
		if (savedInstanceState != null && savedInstanceState.containsKey("cameraImageUri")) {
	    	selectedImageUri = Uri.parse(savedInstanceState.getString("cameraImageUri"));
	   }
		diary_id = b.getInt("DIARY_ID");
		db = new BabyFaceDAO(this);
		eventModel = new EventModel(db);
		myHorizontalLayout = (MyHorizontalLayout)findViewById(R.id.mygallery);
		  Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/SF_Cartoonist_Hand.ttf");
		  eventName = (TextView)findViewById(R.id.eventNameText);
		  eventDesc = (TextView)findViewById(R.id.eventDescText);
		  from_camera = (Button)findViewById(R.id.from_camera_button);
		  from_gallery = (Button) findViewById(R.id.from_gallery_button);
		  eventName.setTypeface(typeface);
		  eventDesc.setTypeface(typeface);
		  
		  
		  overrideFonts(this, (RelativeLayout)findViewById(R.id.add_entry_layout));
    
		  Button post = (Button)findViewById(R.id.postBttn);
    	post.setOnClickListener(new OnClickListener() {
		
		@Override
			public void onClick(View v) {
			// TODO Auto-generated method stub
				if(checkFields()){
					SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
					event.setDiaryID(diary_id);
					event.setEventName(eventName.getText().toString());
					event.setEventDesc(eventDesc.getText().toString());
					event.setEventDate(Integer.parseInt(format.format(Calendar.getInstance().getTime())));
					Log.v("POST DATE", String.valueOf(event.getEventDate()));
					eventModel.addEvent(event, path);
			
					finish();
				}
			}
		});
    
    	from_camera.setOnClickListener(new OnClickListener() {
		
			@Override
			public void onClick(View v) {
				Intent cameraIntent=new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(cameraIntent, PICK_FROM_CAMERA);
			
			}
		});
    	from_gallery.setOnClickListener(new OnClickListener() {
		
			@Override
			public void onClick(View v) {
				final Intent i = new Intent(v.getContext(), com.luminous.pick.MultipleImageLoader.class);
		    	startActivityForResult(i, 200);
			
			}
		});
    
	}
	
	public boolean checkFields(){
		if(eventDesc.getText().toString().isEmpty()){
			Toast toast = Toast.makeText(this, "Please enter a description.", Toast.LENGTH_SHORT);
    		toast.show();
    		return false;
		}
		if(eventName.getText().toString().isEmpty()){
			Toast toast = Toast.makeText(this, "Please enter an event name.", Toast.LENGTH_SHORT);
    		toast.show();
    		return false;
		} 
		if(path == null){
			Toast toast = Toast.makeText(this, "Please select an image for the event.", Toast.LENGTH_SHORT);
    		toast.show();
    		return false;
		}
			
		
		return true;
	}
	
	private void overrideFonts(final Context context, final View v) {
	    try {
	        if (v instanceof ViewGroup) {
	            ViewGroup vg = (ViewGroup) v;
	            for (int i = 0; i < vg.getChildCount(); i++) {
	                View child = vg.getChildAt(i);
	                overrideFonts(context, child);
	         }
	        } else if (v instanceof TextView ) {
	            ((TextView) v).setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/SF_Cartoonist_Hand.ttf"));
	        }
	    } catch (Exception e) {
	    	
	    }
	 }
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
	  super.onSaveInstanceState(savedInstanceState);
	  if (selectedImageUri != null) {
		  savedInstanceState.putString("cameraImageUri", selectedImageUri.toString());
	    }
	}
	
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
	  super.onRestoreInstanceState(savedInstanceState);
	  if (savedInstanceState.containsKey("cameraImageUri")) {
	    	selectedImageUri = Uri.parse(savedInstanceState.getString("cameraImageUri"));
	   }
	}
	
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_CANCELED)
			return;
		try{
			if (requestCode == PICK_FROM_CAMERA) {
				if (data != null) {
					
					selectedImageUri = data.getData();
					finalImagePath = getRealPathFromURI(selectedImageUri);
					isImageSelected = true;
					path = new ArrayList<String>();
					path.add(finalImagePath);
					myHorizontalLayout.add(finalImagePath);
				    Log.v("IMAGE PATH====>>>> ",finalImagePath);
					

				}
			
			}else if(data.getExtras().containsKey("paths")){
				            
				           path =data.getStringArrayListExtra("paths");
				            for(String a:path){
				            	Log.v("IMAGE PATH====>>>> ",a);
				            	myHorizontalLayout.add(a);
				            	Log.v("PATH)_", a);
				            }
				 
			}
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}



	
	
	private String getRealPathFromURI(Uri contentURI) {
	    Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
	    if (cursor == null) { // Source is Dropbox or other similar local file path
	        return contentURI.getPath();
	    } else { 
	        cursor.moveToFirst(); 
	        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA); 
	        return cursor.getString(idx); 
	        
	    }
	    
	    
	}

	
	
}
