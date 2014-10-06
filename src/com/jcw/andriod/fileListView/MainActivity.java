package com.jcw.andriod.fileListView;

/*
 * Created by Jackson Woodruff on 18/07/2014 
 *
 * This is just a simple test case for the different
 * types of view
 */

import android.app.Activity;
import android.os.Bundle;
import android.util.*;
import android.os.*;
import android.widget.Toast;
import com.jcw.andriod.fileListView.dialogs.FileSelectDialog;

import java.io.*;
import java.util.*;

public class MainActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setTheme(android.R.style.Theme_Holo_Light);
        File parentDir = Environment.getExternalStorageDirectory();
        Log.i("Files", Arrays.asList(FileUtils.listDirectories(parentDir)).toString());
        setContentView(new FileSaveView(getApplicationContext()));

		FileSelectDialog fileSelectDialog = new FileSelectDialog(getApplicationContext()) {
			@Override
			public void fileSelected(File selected) {
				Toast.makeText(getContext(), "You selected: " + selected.toString(), Toast.LENGTH_LONG).show();
			}
		};

		fileSelectDialog.setSearchEnabled(true);
		fileSelectDialog.show();
    }
}
