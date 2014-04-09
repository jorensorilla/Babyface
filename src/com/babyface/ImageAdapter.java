package com.babyface;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.babyface.object.Diary;
import com.example.babyface.R;
 
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Diary> diaries;
 
  
    public void setDiaries(ArrayList<Diary> diaries){
    	this.diaries = diaries;
    }
    
    
 
    // Constructor
    public ImageAdapter(Context c, ArrayList<Diary> diaries){
        mContext = c;
        this.diaries = diaries;
    }
 
    @Override
    public int getCount() {
        return diaries.size();
    }
 
    @Override
    public Object getItem(int position) {
        return diaries.get(position);
    }
 
    @Override
    public long getItemId(int position) {
        return 0;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	ImageView imageView;
    	TextView textView;
    	File imgFile;
    	LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.grid_element, parent,
                false);
        Diary d = diaries.get(position);
        imgFile = new  File(d.getImagePath());
        		if(imgFile.exists()){

        			Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

        			imageView = (ImageView) itemView.findViewById(R.id.gridelement_baby_picture);
        			textView = (TextView) itemView.findViewById(R.id.gridelement_nameTextView);
        			
        			textView.setText(d.getBabyName());
        			textView.setTextColor(Color.BLACK);
        			textView.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/SF_Cartoonist_Hand.ttf"));
        			imageView.setImageBitmap(myBitmap);
        			itemView.setBackgroundResource(mContext.getResources().getIdentifier(d.getFrame(), "drawable", mContext.getPackageName()));        		
        			itemView.setTag(d.getDiaryID());
        			
        			itemView.setOnClickListener(new OnClickListener(){

						@Override
						public void onClick(View v) {
							Log.v("LAUNCH", "SUCCESS!");
							
							Intent intent = new Intent(mContext, ViewEntry.class);
							intent.putExtra("DIARY_ID", (Integer)v.getTag());
							mContext.startActivity(intent);
							
						}
        				
        			});

        		}
        
        //ImageView imageView = new ImageView(mContext);
       // imageView.set(mContext.getResources().getDrawable(baby[position]));
       // imageView.setImageDrawable(mContext.getResources().getDrawable(frame[position]));
      
        //imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        //scaleImage(imageView, 50);
       //imageView.setLayoutParams(new GridView.LayoutParams(450, 560));
        return itemView;
    }
    
    
    
    
}
