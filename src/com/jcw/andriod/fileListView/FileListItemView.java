package com.jcw.andriod.fileListView;

/*
 * Author - Woodruff
 * 
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;

public class FileListItemView extends RelativeLayout {
	protected TextView fileName;
	protected TextView metadata;
	protected ImageView icon;

	//this is a risky field to have
	// but it is needed for use in icon generation
	private File file = null;


	public FileListItemView(Context context) {
		super(context);
		init();
	}

	public FileListItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public FileListItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		LayoutInflater inflater = LayoutInflater.from(getContext());
		inflater.inflate(R.layout.file_list_item, FileListItemView.this);

		fileName = (TextView)findViewById(R.id.fileName);
		metadata = (TextView)findViewById(R.id.metaInfo);
		icon = (ImageView)findViewById(R.id.icon);

		icon.setMinimumHeight(32);
		icon.setMinimumWidth(32);
	}

	/*
	returns a string that shows the directory that this
	item represents.

	However, it only returns the **lowest level**, i.e.
	for the folder "storage/sd/example", this would return
	"example"
	 */
	public String getRepresentedDir() {
		return fileName.getText().toString();
	}

	public void setFileName(String fileName) {
		this.fileName.setText(fileName);
	}

	public void setMetadata(String metadata) {
		this.metadata.setText(metadata);
	}

	public void setIcon(int resId) {
		this.icon.setImageResource(resId);
	}

	/*
	 * either returns the whole represented file
	 * or throw an exception.. Should be used with
	 * extreme caution.
	 */
	public File getFullFile() {
		if (file == null) {
			throw new NullPointerException("getFullFile called before setFile");
		} else {
			return file;
		}
	}

	public void setFile(File file) {
		setFileName(file.getName());
		setMetadata(getMetadataText(file));
		this.file = file;
	}

	/*
	 * If this is a type of file that has a picture
	 * associated with it (i.e. a .jpg/png file)
	 * then it will be shown as the picture.
	 * Otherwise, the icon will not be changed
	 */
	public void resetPicture(File thisFile) {
		if (thisFile.isDirectory()) {
			this.setIcon(R.drawable.directory_icon);
		} else if (getRepresentedDir().equals("...")) {
			this.setIcon(R.drawable.directory_up);
		} else {
			PictureGenerator generator = new PictureGenerator(this.file);
			generator.addIconAsync(this.icon);
		}
	}


	/*
	returns by default the size of the file if the
	this represents a file and an empty string if
	 this represents a folder
	 */
	public String getMetadataText(File file) {
		if (file.isFile()) {
			return formatBytes(file.length());
		} else {
			return "";
		}
	}


	/*
	formats a large number of bytes into a normal
	representation (i.e. kB, MB, GB etc.)
	 */

	private static String formatBytes(long byteCount) {
		//keeps track of which postfix is needed
		int index = 0;
		//keeps track of the last 3 digits to display after the
		//decimal point
		int postDecimalPoint = 0;
		final String[] postFixes = new String[] {"bytes", "kb", "mb", "gb", "pb", "eb"};
		while (byteCount >= 1000) {
			index ++;
			//no need to round -- this doesn't have to be
			//perfect
			postDecimalPoint = (int)(byteCount % 1000);
			byteCount /= 1000;
		}

		return Long.toString(byteCount) +
				"." + Integer.toString(postDecimalPoint) +
				" " + postFixes[index];
	}
}
