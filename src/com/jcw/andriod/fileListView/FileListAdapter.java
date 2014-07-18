package com.jcw.andriod.fileListView;/*
 * Author - Woodruff
 * 
 */

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.io.File;

public class FileListAdapter extends BaseAdapter {
	Context context;
	File[] files;

	public FileListAdapter(Context context, File[] files) {

	}

	@Override
	public int getCount() {
		return 0;
	}

	@Override
	public Object getItem(int location) {
		return null;
	}

	@Override
	public long getItemId(int i) {
		return 0;
	}

	@Override
	public View getView(int index, View view, ViewGroup viewGroup) {
		
	}
}
