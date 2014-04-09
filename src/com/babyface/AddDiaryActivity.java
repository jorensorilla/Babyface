package com.babyface;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.babyface.model.DiaryModel;
import com.babyface.object.Diary;
import com.example.babyface.R;

public class AddDiaryActivity extends Activity {
	
	public static final int MALE = 0;
	public static final int FEMALE = 1;
	public static final int NO_GENDER = -1;
	
	private static final int PICK_FROM_CAMERA = 1;
	private static final int PICK_FROM_GALLERY = 2;
	private static final int PICK_FRAME = 3;
	private static final int CROP_IMAGE = 4;
	
	
	private EditText baby_name;
	private EditText baby_pob;
	private ImageButton gender_male;
	private ImageButton gender_female;
	private ImageView profile_image;
	private Calendar cal;
	private EditText date;
	private Button confirm;
	private int gender;
	
	private BabyFaceDAO dao;
	private DiaryModel model;
	private Diary diary;
	
	
	private String finalImagePath;
	private Uri selectedImageUri;
    private boolean isImageSelected;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		setContentView(R.layout.add_diary);
		Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/SF_Cartoonist_Hand.ttf");
		diary = new Diary();
		dao = new BabyFaceDAO(getBaseContext());
		model = new DiaryModel(dao);
		
		
		
		profile_image = (ImageView) findViewById(R.id.pictureImageView);
		baby_name = (EditText) findViewById(R.id.nameEditText);
		baby_name.setTypeface(typeface);
		baby_pob = (EditText) findViewById(R.id.pobEditText);
		baby_pob.setTypeface(typeface);
		gender_male = (ImageButton) findViewById(R.id.maleButton);
		gender_female = (ImageButton) findViewById(R.id.femaleButton);
		cal = Calendar.getInstance();
		date = (EditText)findViewById(R.id.dobEditText);
		date.setTypeface(typeface);
		confirm = (Button)findViewById(R.id.confirmFrame);
		confirm.setTypeface(typeface);
		gender = NO_GENDER;
		gender_male.setBackgroundColor(Color.GRAY);
		gender_female.setBackgroundColor(Color.GRAY);
		setListeners();
		overrideFonts(this, ((RelativeLayout)findViewById(R.id.add_diary_layout)));
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
	
	public boolean checkInput(){
		if(baby_name.getText().toString().isEmpty()){
			Toast toast = Toast.makeText(this, "Please enter your baby's name.", Toast.LENGTH_SHORT);
    		toast.show();
			return false;
		}
		if(baby_pob.getText().toString().isEmpty()){
			
			Toast toast = Toast.makeText(this, "Please enter your baby's place of birth.", Toast.LENGTH_SHORT);
    		toast.show();
    		return false;
		}			
		if(gender == NO_GENDER){
			Toast toast = Toast.makeText(this, "Please select your baby's gender.", Toast.LENGTH_SHORT);
    		toast.show();
			return false;
		}
		if(date.getText().toString().isEmpty()){
			Toast toast = Toast.makeText(this, "Please enter your baby's birth date.", Toast.LENGTH_SHORT);
    		toast.show();
			return false;
		}
		if(selectedImageUri == null){
			Toast toast = Toast.makeText(this, "Please select a profile picture.", Toast.LENGTH_SHORT);
    		toast.show();
			return false;
		}
		
		return  true;
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
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    Log.v("SLEEPING", "IM SLEEPING");
	    if (selectedImageUri != null) {
	        outState.putString("cameraImageUri", selectedImageUri.toString());
	    }
	    if (diary != null) {
	        outState.putString("diary_name", diary.getBabyName());
	        outState.putInt("diary_gender", diary.getDiaryID());
	        outState.putInt("diary_dob", diary.getBabyDOB());
	        outState.putString("diary_pob", diary.getBabyPOB());
	        outState.putString("diary_profile_image", diary.getImagePath());
	    }
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
	    super.onRestoreInstanceState(savedInstanceState);
	    if (savedInstanceState.containsKey("cameraImageUri")) {
	    	selectedImageUri = Uri.parse(savedInstanceState.getString("cameraImageUri"));
	    }
	    
	    if (savedInstanceState.containsKey("diary_name")) {
	    	diary.setBabyName(savedInstanceState.getString("diary_name"));
	    }
	    if (savedInstanceState.containsKey("diary_gender")) {
	    	diary.setGender(savedInstanceState.getInt("diary_gender"));
	    }
	    if (savedInstanceState.containsKey("diary_dob")) {
	    	diary.setBabyDOB(savedInstanceState.getInt("diary_dob"));
	    }
	    if (savedInstanceState.containsKey("diary_pob")) {
	    	diary.setBabyPOB(savedInstanceState.getString("diary_pob"));
	    }
	    if (savedInstanceState.containsKey("diary_profile_image")) {
	    	diary.setImagePath(savedInstanceState.getString("diary_profile_image"));
	    }
	}
	 
	public void setListeners(){
		profile_image.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				AlertDialog.Builder ad = new AlertDialog.Builder(AddDiaryActivity.this);
				ad.setTitle(R.string.select_image_dialog_title);
				ad.setItems(R.array.image_source,
				new DialogInterface.OnClickListener() 
				{
					public void onClick(DialogInterface dialoginterface,int i) 
					{
						switch(i){
							case 0: 
								Intent cameraIntent=new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
								startActivityForResult(cameraIntent, PICK_FROM_CAMERA);
								break;
								
							case 1: Intent intnt = new Intent();
									// call android default gallery
									intnt.setType("image/*");
									intnt.setAction(Intent.ACTION_GET_CONTENT);
									// ******** code for crop image
									/*intnt.putExtra("crop", "true");
									intnt.putExtra("aspectX", 0);
									intnt.putExtra("aspectY", 0);
									intnt.putExtra("outputX", 150);
									intnt.putExtra("outputY", 150);
									
									try {

										intnt.putExtra("return-data", true);
										

									} catch (ActivityNotFoundException e) {
										// Do nothing for now
									}
								   */
									startActivityForResult(Intent.createChooser(intnt,
										"Complete action using"), PICK_FROM_GALLERY);	
							
						}
				}
				})
				.show();
				
			}
			
		});
		gender_male.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(gender == NO_GENDER || gender == FEMALE){
					gender_male.setBackgroundColor(Color.GREEN);
					gender_female.setBackgroundColor(Color.GRAY);
					//gender_male.setImageResource(R.drawable.malesignselected);
					//gender_female.setImageResource(R.drawable.femalesign);
					gender = MALE;
				}else{
					gender_male.setBackgroundColor(Color.GRAY);
					gender = NO_GENDER;
				}
				
				
			}});
		
		gender_female.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(gender == NO_GENDER || gender == MALE){
					gender_female.setBackgroundColor(Color.GREEN);
					gender_male.setBackgroundColor(Color.GRAY);
					gender = FEMALE;
				}else{
					gender_female.setBackgroundColor(Color.GRAY);
					gender = NO_GENDER;
				}
				
			}});
		
		
		date.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new DatePickerDialog(AddDiaryActivity.this,
						 dateListener,
						 cal.get(Calendar.YEAR),
						 cal.get(Calendar.MONTH),
						 cal.get(Calendar.DAY_OF_MONTH)).show();
				
			}
		});
		/*
		date.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				int inType = date.getInputType(); // backup the input type
				date.setInputType(InputType.TYPE_NULL); // disable soft input
				date.onTouchEvent(arg1); // call native handler
				date.setInputType(inType); // restore input type
				return true;
			}
			
			
		});*/
		
		confirm.setOnClickListener(new OnClickListener(){
			//SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			
			@Override
			public void onClick(View v) {
				
				if(checkInput()){
					
						//d = format.parse(date.getText().toString());
						//diary = new Diary();
					   Date d;
				       String formattedDate = null;
				       try {
				    	   d = new SimpleDateFormat("EEEE, MMMM dd yyyy").parse(String.valueOf(date.getText().toString()));
				    	   formattedDate = new SimpleDateFormat("yyyyMMdd").format(d);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						diary.setBabyName(baby_name.getText().toString());
						diary.setBabyPOB(baby_pob.getText().toString());
						diary.setBabyDOB(Integer.parseInt(formattedDate));
						diary.setGender(gender);
						Intent intent = new Intent(getBaseContext(), FrameSelection.class);
						intent.putExtra("gender", gender);
						startActivityForResult(intent, PICK_FRAME);
					
					
				}else
					Toast.makeText(AddDiaryActivity.this, "You lack one of the required inputs!", Toast.LENGTH_LONG);
			}});
	
	}
	
	DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener(){
		@Override
		public void onDateSet(DatePicker view, int year, int month, int dayOfMonth){
			
			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.MONTH, month);
			cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
			
			
			SimpleDateFormat format = new SimpleDateFormat("EEEE, MMMM dd yyyy", Locale.US);
			date.setText(format.format(cal.getTime()));
		}
		
	};
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_diary, menu);
		return true;
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode != Activity.RESULT_CANCELED){
		
		
		
		if (requestCode == PICK_FROM_CAMERA) {
			if (data != null) {
				selectedImageUri = data.getData();
				isImageSelected = true;
				
				//Log.v("IMAGE PATH====>>>> ","DONE!");
				cropImage();

			}
		} else if (requestCode == PICK_FROM_GALLERY) {

			if (data != null) {

			    selectedImageUri = data.getData();
				isImageSelected = true;
				
				//Log.v("IMAGE PATH====>>>> ","DONE!");
				cropImage();

			}

		} else if(requestCode == PICK_FRAME){
			Bundle extras3 = data.getExtras();
			if(extras3 != null){
				String resourceName = extras3.getString("frame");
				diary.setFrame(resourceName);
				model.addDiary(diary);
				finish();
				Log.v("FRAME_ID", "FRAME NAME:"+resourceName);
			}
		}
		else if(requestCode == CROP_IMAGE){
			
			Bundle extras2 = data.getExtras();
			if (extras2 != null) {
				Bitmap photo = extras2.getParcelable("data");
				
				try {
		            FileOutputStream out = new FileOutputStream(getRealPathFromURI(selectedImageUri));
		            photo.compress(Bitmap.CompressFormat.JPEG, 100, out);
		        } catch (FileNotFoundException e) {
		            // TODO Auto-generated catch block
		            e.printStackTrace();
		        }
				
				//finalImageUri = data.getData();
				finalImagePath = getRealPathFromURI(selectedImageUri);
				diary.setImagePath(finalImagePath);
				profile_image.setImageBitmap(photo);
				Log.v("IMAGE PATH====>>>> ",finalImagePath);
			}
		}
		}
	}	
	
	public void cropImage(){
		if(isImageSelected){
	
         Intent intent = new Intent("com.android.camera.action.CROP");
         // this will open all images in the Galery
         intent.setDataAndType(selectedImageUri, "image/*");
         intent.putExtra("crop", true);
         intent.putExtra("outputX", 150);
         intent.putExtra("outputY", 150);
         intent.putExtra("aspectX", 1);
         intent.putExtra("aspectY", 1);
         // true to return a Bitmap, false to directly save the cropped iamge
         intent.putExtra("return-data", true);
         //save output image in uri
         startActivityForResult(intent, CROP_IMAGE);
		}
		
	}
}
