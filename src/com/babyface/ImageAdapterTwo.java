package com.babyface;

import java.io.File;
import java.util.ArrayList;

import com.example.babyface.R;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class ImageAdapterTwo extends PagerAdapter 
{
	Context context;
    private ArrayList<String> _imagePaths;



    public ImageAdapterTwo(Context context, ArrayList<String> imagePaths) 
    {
        this.context = context;
        this._imagePaths = imagePaths;
    }

    
	@Override
    public int getCount() 
    {
        return this._imagePaths.size();
    }
    
    /*Returns the URI of file path*/
    public Uri returnFilePathToURI(int position)
    {    	
    	File file = new File(_imagePaths.get(position));    	
    	Uri currentURI = Uri.fromFile(file.getAbsoluteFile());
    	
		return currentURI;    	
    }
    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        return view == ((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) 
    {
	    
    	Bitmap bitmap = decodeSampledBitmapFromFile(_imagePaths.get(position), 500, 500); 
       
		
		LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.horizontal_view_element, null);
        ImageView imageView = (ImageView)view.findViewById(R.id.horizontal_view_image);
		imageView.setScaleType(ImageView.ScaleType.FIT_XY);

		imageView.setImageBitmap(bitmap);
		((ViewPager) container).addView(view);		
		
		return view;		
		      
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
    
    
    
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) 
    {
        ((ViewPager) container).removeView((View) object);

    }
    
    
    
	
}