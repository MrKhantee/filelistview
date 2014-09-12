package com.jcw.andriod.fileListView;/*
 * Author - Woodruff
 * 
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.io.File;

public class FileListAdapter extends BaseAdapter {
	Context context;
	File[] files;

	public FileListAdapter(Context context, File[] files) {
		this.files = files;
		this.context = context;
	}

	@Override
	public int getCount() {
		return files.length;
	}

	@Override
	public Object getItem(int location) {
		return files[location];
	}

	@Override
	public long getItemId(int i) {
		return 0;
	}

	@Override
	public View getView(int index, View view, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(context);
		FileListItemView rowView = new FileListItemView(context);

		rowView.setFile(files[index]);
		rowView.resetPicture(rowView.getFullFile());
		return rowView;
	}
}
