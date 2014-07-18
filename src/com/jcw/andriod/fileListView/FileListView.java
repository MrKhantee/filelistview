package com.jcw.andriod.fileListView;/*
 * Author - Woodruff
 * 
 */

import android.content.Context;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.File;

public class FileListView extends ListView {
	//feel free to change this to any existing directory
	public File baseDirectory = Environment.getExternalStorageDirectory();

	//holds the list of extensions that cna be used
	//length 0 by default to allow everything
	public String[] fileExtensions = new String[0];

	//if this string is non-empty, only files containing the text in
	//this string will be displayed
	//Also empty by default
	public String searchText = "";

	//default mode is alphabetical
	public SortingMode sortingMode = SortingMode.Alphabetical;

	private FileSelectListener listener;

	public FileListView(Context context) {
		super(context);
		init();
	}

	public FileListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public FileListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		final ListAdapter adapter = getCurrentAdapter();
		this.setAdapter(adapter);
		this.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int index, long id) {
				baseDirectory = baseDirectory + "/" + ((FileListItem)view).getRepresentedDir();
				if (baseDirectory.isFile()) {
					if (listener != null) {
						listener.fileSelected(baseDirectory);
					}
				} else {
					//reset the adapter with a new directory
					ListAdapter adapter = getCurrentAdapter();
					setAdapter(adapter);
				}
			}
		});
	}

	/*
	returns a list adapter containing all the files/folders
	that are in the current directory
	 */
	private ListAdapter getCurrentAdapter() {
		//gets the list of files
		File[] files = baseDirectory.listFiles();
		//filters out unwanted files
		File[] filteredFiles = ListUtils.search(
				ListUtils.filterExtensions(files, fileExtensions), searchText);
		//sorts the remaining files accoring to current mode
		File[] sortedFiles = this.sortingMode.sort(filteredFiles);
		return new FileListAdapter(getContext(), sortedFiles);
	}

	public void loadLastDir() {
		//todo -- sets the baseDirectory to the last dir that this accessed
	}

	public void setExtensions(String[] extensions) {
		this.fileExtensions = extensions;
	}

	public void searchWith(String text) {
		this.searchText = text;
	}

	public void setSortingMode(SortingMode newMode) {
		this.sortingMode = newMode;
	}

	public void setFileSelectedListener(FileSelectListener listener) {
		this.listener = listener;
	}

	public interface FileSelectListener {
		public void fileSelected(File selected);
	}

	public enum SortingMode {
		Alphabetical,
		OldestNewest,
		NewestOldest;

		SortingMode() {

		}

		public File[] sort(File[] list) {
			switch (this) {
				case Alphabetical:
					return ListUtils.sortByName(list);
				case OldestNewest:
					return ListUtils.sortByDate(list);
				case NewestOldest:
					return ListUtils.sortNewestToOldest(list);
			}
		}
	}
}

