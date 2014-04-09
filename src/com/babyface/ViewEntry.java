package com.babyface;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.PopupMenu;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.babyface.model.DiaryModel;
import com.babyface.model.EventModel;
import com.babyface.model.ImageModel;
import com.babyface.object.Diary;
import com.babyface.object.Event;
import com.example.babyface.R;


public class ViewEntry extends Activity implements PopupMenu.OnMenuItemClickListener  {
	private CustomAdapter adapter;
	private  List<Item> items;
	private ListView listView;
	private BabyFaceDAO db;
	private DiaryModel diaryModel;
	private EventModel eventModel;
	private ImageModel imageModel;
	private Diary diary;
	private ArrayList<Event> eventList;
	private int diary_id;
	private PopupMenu.OnMenuItemClickListener listener;
	private Event selectedEvent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_entry);
		listView = (ListView) findViewById(R.id.listview);
		db = new BabyFaceDAO(this);
		diaryModel = new DiaryModel(db);
		//model.addDiary();
		Bundle b = getIntent().getExtras();
		diary_id = b.getInt("DIARY_ID");
		 
		 eventModel = new EventModel(db);
		 imageModel = new ImageModel(db);
			
				
		
		 diary = diaryModel.getDiary(diary_id);
		 eventList = eventModel.getAllEvents(diary_id);		
		 
		 for(Event e:eventList)
			 e.setFirstImagePath(imageModel.getFirstImagePath(e.getEventID()));
		 
		
		 adapter = new CustomAdapter(ViewEntry.this, eventList, diary);
		 listView.setAdapter(adapter);
		 this.listener = this;
		 listView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				selectedEvent = (Event)parent.getItemAtPosition(position);
				PopupMenu popup = new PopupMenu(ViewEntry.this, view);
			    MenuInflater inflater = popup.getMenuInflater();
			    inflater.inflate(R.menu.entry_menu, popup.getMenu());
			    popup.setOnMenuItemClickListener(listener);
			    popup.show();
				
			}
			 
		 } 
		);
		
		

	}
	
	 
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.v("resumed", "ViewEntry HAS RESUMED");
		adapter.clear();

		
		eventList = eventModel.getAllEvents(diary_id);
		for(Event e:eventList)
			 e.setFirstImagePath(imageModel.getFirstImagePath(e.getEventID()));
		adapter.update(eventList, diaryModel.getDiary(diary_id));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		getMenuInflater().inflate(R.menu.event_view, menu);
		return true;
	}
	
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	    case R.id.add_view:
	    	
	    	Intent i = new Intent(this, AddEntry.class);
	    	i.putExtra("DIARY_ID", diary_id);
	    	startActivity(i);
	      return true;
	    
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}




	@Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
            	Intent i = new Intent(this, EntryView.class);
	    		i.putExtra("event_id", selectedEvent.getEventID());
	    		startActivity(i);
                return true;
            case R.id.delete:
            	
            	AlertDialog.Builder builder = new AlertDialog.Builder(this);
            	builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    	eventModel.removeEvent(selectedEvent.getEventID());
                    	adapter.clear();
                		eventList = eventModel.getAllEvents(diary_id);
                		for(Event e:eventList)
                			 e.setFirstImagePath(imageModel.getFirstImagePath(e.getEventID()));
                		adapter.update(eventList, diaryModel.getDiary(diary_id));
                    
                    	
                    
                    	
                    }
                });
            	builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
            	
            	builder.setMessage(R.string.delete_event_message)
            	       .setTitle(R.string.delete_event_title);

            	
            	AlertDialog dialog = builder.create();
            	dialog.show();
                return true;
            default:
                return false;
        }
    }
	
	 
}
