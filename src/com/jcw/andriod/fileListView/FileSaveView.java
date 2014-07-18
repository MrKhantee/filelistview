package com.jcw.andriod.fileListView;

/*
 * Created by Jackson Woodruff on 18/07/2014 
 *
 * This class is very similar to the FileOpenView --
 * just two changes. The button and edittext are now
 * mandatory, and they are used for entering (edittext)
 * and confirming (button) the name of the file to
 * save as
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import scala.NotImplementedError;

import java.io.File;

public class FileSaveView extends FileOpenView {
	private EditText fileName;
	private Button confirm;

	private FileListView.FileSelectListener listener;

	//when set to false, no warning will be shown when overwriting files
	public boolean showOverwriteWarning = true;

	public FileSaveView(Context context) {
		super(context);
		init();
	}

	public FileSaveView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public FileSaveView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	@Override
	protected void init() {
		this.fileName = search;
		this.confirm = folderSelect;
		super.init();
		fileName.setHint(R.string.fileName);
		confirm.setText(R.string.save);
	}

	@Override
	protected void setListeners() {
		listView.setFileSelectedListener(new FileListView.FileSelectListener() {
			@Override
			public void fileSelected(File selected) {
				fileName.setText(selected.getName());
			}
		});

		fileName.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

			}

			@Override
			public void afterTextChanged(Editable editable) {
				//todo -- potentially fill this in so that the save button is disabled
				//todo -- if the file already exists
			}
		});

		confirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				//if the filename is empty, then the user needs to know
				if (fileName.getText().toString().trim().equals("")){
					fileName.setError(getContext().getResources().getString(R.string.fileNeedsName));
					return;
				}
				final File file = new File(listView.baseDirectory + "/" + fileName.getText().toString());
				if (file.exists() && showOverwriteWarning) {
					AlertDialog.Builder b = new AlertDialog.Builder(getContext());
					b.setTitle(R.string.fileAlreadyExists);
					b.setMessage(R.string.overwriteMessage);
					b.setPositiveButton(R.string.overwrite, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialogInterface, int i) {
							if (listener != null)
								listener.fileSelected(file);
						}
					});
					b.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialogInterface, int i) {
							//also do nothing
						}
					});
					b.show();
					return;
				}
				//passed all tests
				if (listener != null)
					listener.fileSelected(file);
			}
		});
	}

	@Override
	public void setFolderSelectEnabled(boolean enabled) {
		throw new NotImplementedError("There is no folder select button to hide in a " +
				"FileSaveView");
	}

	@Override
	public void setSearchEnabled(boolean enabled) {
		throw new NotImplementedError("There is no search bar to hide in a " +
				"FileSaveView");
	}

	public void enableOverwriteWarning(boolean enabled) {
		this.showOverwriteWarning = enabled;
	}

	public void setFileSelectedListener(FileListView.FileSelectListener listener) {
		this.listener = listener;
	}
}
