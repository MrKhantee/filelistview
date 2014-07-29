package com.jcw.andriod.fileListView;

/*
 * Created by Jackson Woodruff on 18/07/2014 
 *
 * This is just a simple test case for the different
 * types of view
 */

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);
		setTheme(android.R.style.Theme_Holo_Light);
		setContentView(new FileListView(getApplicationContext()));
	}
}
