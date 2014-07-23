package com.jcw.andriod.fileListView;/*
 * Created by Jackson Woodruff on 18/07/2014 
 *
 * This is a view with a few utility methods
 * that basically contains a FileListView.
 * It is designed for the selection of an
 * existing file (I designed it for opening
 * files)
 *
 * For file selection that enables the selection
 * of uncreated files, use the FileSaveView
 */

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import java.io.File;

public class FileOpenView extends RelativeLayout {
	private FileListView.FileSelectListener listener;

	//when disabled, the search bar will be hidden
	public boolean searchEnabled = true;

	//when true, folder selection will be enabled.
	//This adds a button to the top of the view
	//the select the current folder
	public boolean folderSelectMode = false;

	//edittext for searching through the current displayed files
	public EditText search;
	//button for selecting the current folder
	public Button folderSelect;
	//the file view. Public so changes
	//can be made to it (sorting order etc..)
	public FileListView listView;

	public FileOpenView(Context context) {
		super(context);
		init();
	}

	public FileOpenView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public FileOpenView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	protected void init() {
		LayoutInflater inflater = LayoutInflater.from(getContext());
		inflater.inflate(R.layout.file_open_view, FileOpenView.this);

		search = (EditText)findViewById(R.id.searchBar);
		folderSelect = (Button)findViewById(R.id.folderSelectButton);
		listView = (FileListView)findViewById(R.id.fileListView);

		//set the visibility of the button and search bar to default
		setSearchEnabled(searchEnabled);
		setFolderSelectEnabled(folderSelectMode);

		setListeners();
	}

	protected void setListeners() {
		listView.setFileSelectedListener(new FileListView.FileSelectListener() {
			@Override
			public void fileSelected(File selected) {
				if (listener != null) {
					listener.fileSelected(selected);
				}
			}
		});

		folderSelect.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if (listener != null) {
					listener.fileSelected(listView.baseDirectory);
				}
			}
		});

		search.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

			}

			@Override
			public void onTextChanged(CharSequence text, int i, int i2, int i3) {
				//maybe put the searching in here for more real time updating
			}

			@Override
			public void afterTextChanged(Editable editable) {
				listView.searchText = editable.toString();
				listView.refresh();
			}
		});
	}

	public void setSearchEnabled(boolean enabled) {
		this.searchEnabled = enabled;
		//clear the search text
		listView.searchText = "";
		//set the visibility as required
		if (enabled) {
			search.setVisibility(View.VISIBLE);
		} else {
			search.setVisibility(View.GONE);
		}
		listView.refresh();
	}

	public void setFolderSelectEnabled(boolean enabled) {
		this.folderSelectMode = enabled;
		//change whether files are shown
		listView.setDirectoriesOnly(folderSelectMode);
		//reset visibility
		if (enabled) {
			folderSelect.setVisibility(View.VISIBLE);
		} else {
			folderSelect.setVisibility(View.GONE);
		}
		listView.refresh();
	}

	public void setFileSelectedListener(FileListView.FileSelectListener listener) {
		this.listener = listener;
	}
}
