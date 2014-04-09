package com.babyface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.babyface.model.EventModel;
import com.babyface.object.Diary;
import com.babyface.object.Event;
import com.example.babyface.R;

public class CustomAdapter extends ArrayAdapter<Event>  {
	   
	    private List<Event> events;
	    private ViewHolder vh;
	    private Diary diary;
	    private String imagePath;
	    private Event event;
	    private Context mContext;
	    private static final int PROFILE_ITEM = 0;
	    private static final int EVENT_ITEM = 1;
	    
	    private ArrayAdapter<Event> adapter;
	    
	    
	    
	    public void update(List<Event> events, Diary diary){
	    	this.events = events;
	    	this.diary = diary;
	    	
	    }

	    public CustomAdapter(Context context, List<Event> events, Diary diary) {
	    	super(context, 0, events);
	        this.events = events;
	        this.diary = diary;
	        mContext = context;
	        this.adapter = this;
	        adapter.addAll(events);

	    }
	   
	    @Override
	    public Event getItem(int position){
	    	return events.get(position-1);
	    }
	    @Override
	    public int getViewTypeCount() {
	        return 2;

	    }
	 
	    
	    @Override
	    public int getCount(){
	    	return events.size()+1;
	    }
	    @Override
      public int getItemViewType(int position) {
          if(position == 0)
          	return PROFILE_ITEM;
          else 
          	return EVENT_ITEM;
      }
	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	    	 int type = getItemViewType(position);
	    	 
	        	if (convertView == null) {
	        		vh = new ViewHolder();
	        		
	        		switch(type){
	        			case PROFILE_ITEM:convertView = makeProfileItem(convertView);
	        						  		break;
	        			case EVENT_ITEM: convertView = makeEventItem(convertView);
	        						  		break;
	        						  
	        			default: break;
	        		}
		            
		        } else {
		        
		            	vh=(ViewHolder) convertView.getTag();

		        }
	        	if(type == EVENT_ITEM){
	        	   
	        		
	               
		           event = getItem(position);
		           Log.v("EVENT", "ID: "+String.valueOf(event.getEventName()));
			       Date d;
			       String formattedDate = null;
			       try {
			    	   d = new SimpleDateFormat("yyyyMMdd").parse(String.valueOf(event.getEventDate()));
			    	   formattedDate = new SimpleDateFormat("EEEE, MMMM dd yyyy").format(d);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			       
			       vh.date.setText(String.valueOf(formattedDate));
			       vh.event_id = event.getEventID();
			       vh.event.setText(event.getEventName());
			   	   imagePath = event.getFirstImagePath();  
			   	   
			   	  
			       if(imagePath != null)
			       {
			    	   try{	    	   
			    		 
			    		   Bitmap bm = decodeSampledBitmapFromFile(imagePath,500,500);
			    		   vh.image.setImageBitmap(bm);	  
			    	    
			    		}catch(Exception a)
			    		{
		    	    		
		    	    	}
			       }
			       
	        	}
	        	return convertView;	
	        
	    	
	    }
	    
	    static class ViewHolder {
	    	  TextView event;
	    	  TextView date;
	    	  ImageView image;
	    	 
	    	  AsyncTask<ViewHolder, ?, ?> task;
	    	  int event_id;
	   	}
	    
	    private View makeEventItem(View convertView){
	    	LayoutInflater inflater = (LayoutInflater) mContext
                 .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         convertView = inflater.inflate(R.layout.event_layout, null);
         Typeface typeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/SF_Cartoonist_Hand.ttf");
         vh.event=(TextView) convertView.findViewById(R.id.eventName);
         vh.event.setTypeface(typeface);
         vh.date=(TextView) convertView.findViewById(R.id.date);
         vh.date.setTypeface(typeface);
         vh.image=(ImageView)convertView.findViewById(R.id.viewImage);
         vh.image.setFocusable(false);
         

         convertView.setTag(vh);
 
		       return convertView;
	    }
	    
	    private View makeProfileItem(View convertView){
	    	View view;
	       
	        LayoutInflater inflater = (LayoutInflater) mContext
	                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        view = (View) inflater.inflate(R.layout.profile_item, null);
	           
	        
	        Typeface typeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/SF_Cartoonist_Hand.ttf");
	        TextView name = (TextView) view.findViewById(R.id.nameText);
	        name.setTypeface(typeface);
	        name.setText(diary.getBabyName());
	        
	        Date d;
		    String formattedDate = null;
		    try {
		    	d = new SimpleDateFormat("yyyyMMdd").parse(String.valueOf(diary.getBabyDOB()));
		    	formattedDate = new SimpleDateFormat("MMM dd yyyy").format(d);
		    } catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        TextView birthDate = (TextView) view.findViewById(R.id.birthDateText);
	        birthDate.setText(formattedDate);
	        birthDate.setTypeface(typeface);
	      
	        TextView birthPlace = (TextView) view.findViewById(R.id.birthPlaceText);
	        birthPlace.setText(diary.getBabyPOB());
	        birthPlace.setTypeface(typeface);
	        
	        ImageView profileImage = (ImageView) view.findViewById(R.id.profileImage);
	        profileImage.setImageBitmap(BitmapFactory.decodeFile(diary.getImagePath()));
	        
	        ImageView genderImage = (ImageView)view.findViewById(R.id.genderImage);
	        if(diary.getGender()==0)
	        	genderImage.setImageResource(R.drawable.malesign);
	        else
	        	genderImage.setImageResource(R.drawable.femalesign);
	       
	        overrideFonts(mContext, (RelativeLayout)view.findViewById(R.id.relativeLayout1));
	        return view;
	    	
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
	    
	    /*Scale down the large images for the view pager bitmaps*/
	    public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight) 
	    { 

	        // First decode with inJustDecodeBounds=true to check dimensions
	        final BitmapFactory.Options options = new BitmapFactory.Options();
	        options.inJustDecodeBounds = true;
	        BitmapFactory.decodeFile(path, options);

	        //Calculate inSampleSize
	            // Raw height and width of image
	            final int height = options.outHeight;
	            final int width = options.outWidth;
	            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
	            int inSampleSize = 1;

	            if (height > reqHeight) {
	                inSampleSize = Math.round((float)height / (float)reqHeight);
	            }

	            int expectedWidth = width / inSampleSize;

	            if (expectedWidth > reqWidth) {
	                //if(Math.round((float)width / (float)reqWidth) > inSampleSize) // If bigger SampSize..
	                inSampleSize = Math.round((float)width / (float)reqWidth);
	            }
	            
	            options.inSampleSize = inSampleSize;

	            // Decode bitmap with inSampleSize set
	            options.inJustDecodeBounds = false;

	                return BitmapFactory.decodeFile(path, options);
	    }
	    
	    
	    
	    

}

