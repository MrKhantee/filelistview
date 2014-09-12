package com.jcw.andriod.fileListView;

/*
 * Created by Jackson Woodruff on 12/09/2014 
 *
 *
 * This is a protected class that is used
 * in the list view to generate the images
 * for certain types of file.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.io.File;

class PictureGenerator {
	File file;
	FileExtension extension;


	public PictureGenerator(File file) {
		this.file = file;
		extension = FileExtension.createExtension(getExtension());
	}

	/*
	 * you should avoid calling this
	 * on the main thread -- it will
	 * get very expensive very fast
	 * because it loads the whole
	 * picture into memory then trims
	 * it.
	 */
	public Bitmap getIcon() {
		return extension.getIcon(this.file);
	}

	/*
	 * This MUST be called within a thread
	 *
	 * This is private because
	 * if it is called many times at
	 * once then you run into out
	 * of memory errors
	 *
	 * Hence, this SHOULD BE USED WITH
	 * EXTREME CAUTION
	 */
	protected void addIconAsync(final ImageView view) {
		final Bitmap icon = getIcon();
		view.post(new Runnable() {
			@Override
			public void run() {
				if (icon == null) {
					//the icon was not loaded here
					view.setImageResource(R.drawable.file_icon);
				} else {
					view.setImageBitmap(icon);
				}
			}
		});
	}

	/*
	 * This adds images to a series of views in
	 * async fashion.
	 */
	public static void addIconsAsync(final FileListItemView[] items) {
		Thread runnable = new Thread(new Runnable() {
			@Override
			public void run() {
				for (FileListItemView view : items) {
					PictureGenerator generator = new PictureGenerator(view.getFullFile());
					generator.addIconAsync(view.icon);
				}
			}
		});
	}

	private String getExtension() {
		String[] delimited = file.getName().split("\\.");
		if (delimited.length == 0) {
			return "";
		} else {
			return delimited[delimited.length - 1].toLowerCase();
		}
	}

	private enum FileExtension {
		JPG(".jpg"),
		PNG(".png"),
		Other("");

		protected String extension;

		FileExtension(String extension) {
			this.extension = extension;
		}

		/*
		 * returns a bitmap in 64x64 format
		 * of the passed picture
		 */
		public Bitmap getIcon(File file) {
			switch (this) {
				case PNG:
				case JPG:
					return getPictureIcon(file);
				case Other:
					return null;
			}
			return null;
		}

		private Bitmap getPictureIcon(File file) {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inPreferredConfig = Bitmap.Config.ARGB_8888;
			Bitmap fullSized = BitmapFactory.decodeFile(file.toString(), options);
			Bitmap resized = Bitmap.createScaledBitmap(fullSized, 64, 64, true);
			fullSized.recycle();
			return resized;
		}

		public static FileExtension createExtension(String extension) {
			String s = extension.toLowerCase();
			if (s.equals("jpg")) {
				return JPG;
			} else if (s.equals("png")) {
				return PNG;
			} else {
				return Other;
			}
		}
	}
}
