package com.jcw.andriod.fileListView;

/*
 * Author - Woodruff
 * todo -- add up option as top list item
 */

import android.content.Context;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.File;
import android.graphics.*;

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

	//when true, only files that are actually directories will be shown
	public boolean directoriesOnly = false;


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
		this.setCacheColorHint(Color.TRANSPARENT);
		this.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int index, long id) {
				if (index == 0) {//this is the up item
					File up = baseDirectory.getParentFile();
					if (up == null || up.listFiles() == null) {//already top level directory
						return;
					} else {
						baseDirectory = up;
						setAdapter(getCurrentAdapter());
					}
				}
				baseDirectory = new File(baseDirectory + "/" + ((FileListItemView) view).getRepresentedDir());
				if (!baseDirectory.isDirectory()) {
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
		//first get rid of the directories
		File[] filteredFiles = ListUtils.directoriesOnly(
				//then anything that doesn't contain the search term
				ListUtils.search(
				//finally anything that doesn't match the specified extensions
				ListUtils.filterExtensions(files, fileExtensions),
				searchText), directoriesOnly);
		//sorts the remaining files accoring to current mode
		File[] sortedFiles = this.sortingMode.sort(filteredFiles);
		File[] upIncluded = new File[sortedFiles.length + 1];
		upIncluded[0] = new File("...");
		
		for (int i = 0; i < sortedFiles.length; i++) {
			upIncluded[i + 1] = sortedFiles[i];
		}
		return new FileListAdapter(getContext(), upIncluded);
	}

	public void loadLastDir() {
		File newFile = baseDirectory.getParentFile();
		if (newFile == null) //this is the top directory -- no parent
			return;

		baseDirectory = newFile;
	}

	public void setExtensions(String[] extensions) {
		this.fileExtensions = extensions;
	}

	public void searchWith(String text) {
		this.searchText = text;
	}

	public void setDirectoriesOnly(boolean directoriesOnly) {
		this.directoriesOnly = directoriesOnly;
	}

	public void setSortingMode(SortingMode newMode) {
		this.sortingMode = newMode;
	}

	public void refresh() {
		setAdapter(getCurrentAdapter());
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
					return ListUtils.sortNewestOldest(list);
				default:
					throw new Error("If you add a new type to sorting mode, you need to update the" +
							" SortingMode.sort method to handle this new option");
			}
		}
	}
}

