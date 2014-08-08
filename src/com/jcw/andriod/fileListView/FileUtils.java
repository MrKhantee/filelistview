package com.jcw.andriod.fileListView;
import java.io.*;

/*
 * Author - Woodruff
 * 
 */

public class FileUtils {
	/*
	this is a method that gets out the FILES
	from a given directory
	*/
	public static File[] listFiles(File parentDir) {
		return parentDir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String filename) {
				return !(new File(dir + "/" + filename).isDirectory());
			}
		});
	}
	
	/*
	returns a list of DIRECTORIES in the parent file
	*/
	public static File[] listDirectories(File parentDir){
		return parentDir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String filename) {
				return new File(dir + "/" + filename).isDirectory();
			}
		});
	}
}
