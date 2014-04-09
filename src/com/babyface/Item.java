package com.babyface;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

public interface Item {
    public int getViewType();
    public View getView(LayoutInflater inflater, View convertView);
	
}