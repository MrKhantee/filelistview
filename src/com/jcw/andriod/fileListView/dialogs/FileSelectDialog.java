package com.jcw.andriod.fileListView.dialogs;

/*
 * Created by Jackson Woodruff on 04/10/2014 
 *
 * This is a simple dialog adaptation of the listview
 * to enable the selection of files in a separate window
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import com.jcw.andriod.fileListView.FileListView;
import com.jcw.android.fileListView.R;

import java.io.File;

public abstract class FileSelectDialog extends AlertDialog.Builder {
	public FileListView listView;
	private EditText searchEditText;

	private boolean searchEnabled = false;

	public FileSelectDialog(Context context) {
		super(context);
		this.listView = new FileListView(context);

		init();
	}

	public abstract void fileSelected(File selected);

	private void init() {
		this.setView(listView);

		listView.setFileSelectedListener(new FileListView.FileSelectListener() {
			@Override
			public void fileSelected(File selected) {
				fileSelected(selected);
			}
		});

		this.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				//let the dialog cancel on its own
			}
		});

		//todo -- intitalize the search edittext
	}

	public void setSearchEnabled(boolean enabled) {
		this.searchEnabled = enabled;
		if (enabled) {
			super.setCustomTitle(searchEditText);
		}
	}

	@Override
	public FileSelectDialog setView(View view) {
		//throw an exception here because this simply cannot be done.
		//I am already using the view for the list view.
		throw new Error("You cannot customize the view of a FileSelectDialog, " +
				"this feature would override the actual list");
	}

	@Override
	public FileSelectDialog setCustomTitle(View view) {
		//this cannot be done if searching files is enabled, because
		//the title is used to store the edittext
		if (searchEnabled) {
			throw new Error("You cannot use the title of a FileSelectDialog" +
					"when searching is enabled");
		} else {
			return (FileSelectDialog)super.setCustomTitle(view);
		}
	}
}
