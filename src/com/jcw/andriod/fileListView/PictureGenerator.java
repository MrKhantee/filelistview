package com.jcw.andriod.fileListView;

/*
 * Created by Jackson Woodruff on 12/09/2014
 *
 *
 * This is a protected class that is used
 * in the list view to generate the images
 * for certain types of file.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.jcw.android.fileListView.R;

import java.io.File;

class PictureGenerator {
	File file;
	FileExtension extension;

	public static final int ICON_SIZE = 64;

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
	public Bitmap getIcon(Context context) {
		return extension.getIcon(context, this.file);
	}

	/*
	 * This MUST be called within a thread
	 */
	protected void addIconAsync(final ImageView view) {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				final Bitmap icon = getIcon(view.getContext());
				Runnable setImageRunnable = new Runnable() {
					@Override
					public void run() {
						if (icon == null) {
							//the icon was not loaded here
							view.setImageResource(R.drawable.file_icon);
						} else {
							view.setImageBitmap(icon);
						}
					}
				};

				try {
					view.getHandler().post(setImageRunnable);
				} catch (Exception e) {
					//this is really trying to catch a dead object
					//exception, which means that the view is already destroyed
					view.post(setImageRunnable);
				}
			}
		});
		thread.start();
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
		//IMPORTANT NOTE
		//if you add something here, you also have to add it to the
		//getIcon and createExtension methods
		JPG(".jpg"),
		PNG(".png"),
		MP3(".MP3"),
		MP4(".MP4"),
		AVI(".AVI"),
		FLV(".FLV"),
		MOV(".MOV"),
		Other("");

		protected String extension;

		FileExtension(String extension) {
			this.extension = extension;
		}

		/*
		 * returns a bitmap in 64x64 format
		 * of the passed picture
		 */
		public Bitmap getIcon(Context context, File file) {
			switch (this) {
				case PNG:
				case JPG:
					return getPictureIcon(file);
				case MP3:
					return getPictureFromResource(context, R.drawable.mp3_icon);
				case Other:
					return null;
			}
			return null;
		}

		private Bitmap getPictureFromResource(Context context, int resId) {
			return BitmapFactory.decodeResource(context.getResources(), resId);
		}

		/* This method is synchronized as many calls can be generated to it
		 * from a single view.  (All in parallel)  If that is done, then
		 * we don't want to run out of memory loading a gazillion of these
		 * bitmaps into memory.
		 */
		private synchronized Bitmap getPictureIcon(File file) {
			try {
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inPreferredConfig = Bitmap.Config.ARGB_8888;
				Bitmap fullSized = BitmapFactory.decodeFile(file.toString(), options);
				Bitmap resized = Bitmap.createScaledBitmap(fullSized, 64, 64, true);
				fullSized.recycle();
				return resized;
			} catch (Exception e) {
				//This can happen if there is an improperly formatted png/jpg file
				//i.e. if someone changed the extension of a file name by accident
				return null;
			}
		}

		public static FileExtension createExtension(String extension) {
			String s = extension.toLowerCase();
			if (s.equals("jpg")) {
				return JPG;
			} else if (s.equals("png")) {
				return PNG;
			} else if (s.equals("mp3")) {
				return MP3;
			} else {
				return Other;
			}
		}
	}
}
