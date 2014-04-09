package com.babyface;

import com.example.babyface.R;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;

import android.support.v4.app.FragmentManager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class FrameSelection extends FragmentActivity {
	private LinearLayout layout;
	private ImageView selectedImage;  
    private Integer[] mImageIds;
    private Button confirm;
    private int gender;
   
    
    

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE); 
		setContentView(R.layout.activity_frame_selection);
        Bundle extras;
		
		//layout = (LinearLayout) findViewById(R.id.layout);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/SF_Cartoonist_Hand.ttf");
		confirm = (Button) findViewById(R.id.confirmFrame);
		confirm.setTypeface(typeface);
		mPager = (ViewPager) findViewById(R.id.pager);
		
		
        
        
		//selectedImage = (ImageView) findViewById(R.id.selectedFrame);
		
		if (savedInstanceState == null) {
		    extras = getIntent().getExtras();
		    if(extras == null) {
		        gender = -1;
		    } else {
		        gender= extras.getInt("gender");
		    }
		} else {
			gender= (Integer)savedInstanceState.getSerializable("gender");
		}
		
		setFrames();
		setListeners();
		
		mPagerAdapter = new ScreenSlidePagerAdapter(getBaseContext(), mImageIds);
		mPager.setAdapter(mPagerAdapter);
        // Instantiate a ViewPager and a PagerAdapter.
        
    }
    
    
	
	public void setFrames(){
		
		if(gender == AddDiaryActivity.MALE){
			mImageIds = new Integer[]{
					R.drawable.boyframe1,
					R.drawable.boyframe2,
					R.drawable.boyframe3,
					R.drawable.boyframe4,
					R.drawable.boyframe5,
					R.drawable.boyframe6,
					R.drawable.boyframe7
				};
			
		}else if(gender == AddDiaryActivity.FEMALE){
			mImageIds = new Integer[]{
					R.drawable.girlframe1,
					R.drawable.girlframe2,
					R.drawable.girlframe3,
					R.drawable.girlframe4,
					R.drawable.girlframe5,
					R.drawable.girlframe6,
					R.drawable.girlframe7
				};
			
		}
		
		
		
	}
	
	public void setListeners(){
		confirm.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
					
					Intent intent = getIntent();
					intent.putExtra("frame", getResources().getResourceEntryName(mImageIds[mPager.getCurrentItem()]));
					setResult(RESULT_OK, intent);
					finish();
				
				
				
			}
			
		});
	}
	
	
	
	
	
	
    

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends PagerAdapter {
    	private LayoutInflater inflater;
    	private Context context;
    	private Integer[] frames;
        public ScreenSlidePagerAdapter(Context c, Integer[] frames) {
			// TODO Auto-generated constructor stub
        	 context = c;
        	 this.frames = frames;
		}



        @Override
        public int getCount() {
            return frames.length;
        }
        
       
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((LinearLayout) object);
        }
     
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
     
            // Declare Variables
             
            ImageView imgflag;
     
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = inflater.inflate(R.layout.frame_view_layout, container,
                    false);
     
            
            imgflag = (ImageView) itemView.findViewById(R.id.image_fragment);
            imgflag.setImageDrawable(context.getResources().getDrawable(frames[position]));
            ((ViewPager) container).addView(itemView);
     
            return itemView;
        }
     
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // Remove viewpager_item.xml from ViewPager
            ((ViewPager) container).removeView((LinearLayout) object);
     
        }
        
        
    }

	

}
