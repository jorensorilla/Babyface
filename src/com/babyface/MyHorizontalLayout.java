package com.babyface;

import java.util.ArrayList;

import com.example.babyface.R;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

	public class MyHorizontalLayout extends LinearLayout {
	 
		Context myContext;
		 ArrayList<String> itemList = new ArrayList<String>();

		 public MyHorizontalLayout(Context context) {
		  super(context);
		  myContext = context;
		 }

		 public MyHorizontalLayout(Context context, AttributeSet attrs) {
		  super(context, attrs);
		  myContext = context;

		 }

		 @TargetApi(Build.VERSION_CODES.HONEYCOMB)
		public MyHorizontalLayout(Context context, AttributeSet attrs,
		   int defStyle) {
		  super(context, attrs, defStyle);
		  myContext = context;
		 }
		 
		 public void add(String path){
		  int newIdx = itemList.size();
		  itemList.add(path);
		  
		  LayoutInflater inflater = (LayoutInflater) myContext
	                 .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		  View view = inflater.inflate(R.layout.horizontal_view_element, null);
		  ImageView image = (ImageView)view.findViewById(R.id.horizontal_view_image);
		  setImageView(newIdx, image);
		  addView(view);
		 }
		 
		 ImageView setImageView(final int i, ImageView imageView){
		  Bitmap bm = null;
		  if (i < itemList.size()){
		   bm = decodeSampledBitmapFromUri(itemList.get(i), 250, 220);
		  }
		
		  imageView.setScaleType(ImageView.ScaleType.FIT_XY);
		  imageView.setImageBitmap(bm);

		  return imageView;
		 }
		 
		 public Bitmap decodeSampledBitmapFromUri(String path, int reqWidth, int reqHeight) {
		     Bitmap bm = null;
		     
		     // First decode with inJustDecodeBounds=true to check dimensions
		     final BitmapFactory.Options options = new BitmapFactory.Options();
		     options.inJustDecodeBounds = true;
		     BitmapFactory.decodeFile(path, options);
		     
		     // Calculate inSampleSize
		     options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		     
		     // Decode bitmap with inSampleSize set
		     options.inJustDecodeBounds = false;
		     bm = BitmapFactory.decodeFile(path, options); 
		     
		     return bm;  
		    }
		    
		    public int calculateInSampleSize(
		      
		     BitmapFactory.Options options, int reqWidth, int reqHeight) {
		     // Raw height and width of image
		     final int height = options.outHeight;
		     final int width = options.outWidth;
		     int inSampleSize = 1;
		        
		     if (height > reqHeight || width > reqWidth) {
		      if (width > height) {
		       inSampleSize = Math.round((float)height / (float)reqHeight);   
		      } else {
		       inSampleSize = Math.round((float)width / (float)reqWidth);   
		      }   
		     }
		     
		     return inSampleSize;   
		    }
}

