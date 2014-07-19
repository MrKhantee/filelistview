package com.jcw.andriod.fileListView;

/*
 * Created by Jackson Woodruff on 19/07/2014 
 *
 *
 * This is a simple utils class that contains methods
 * such as search, sort, etc..
 *
 * Mainly used to stop the bloating of the FileListView class
 *
 * It is also a replacement of the ListUtils.scala to
 * remove the dependency on the (rather large) scala
 * library.
 */

import java.io.File;
import java.util.*;

public class ListUtils {

	//takes a list of files and a list of
	//accepted file types and filers them accordingly
	//returns the input list on empty input
	public static File[] filterExtensions(File[] files, String[] extensions) {
		//if there are no extensions passed, then all should be okay
		if (extensions.length == 0)
			return files;

		List<File> filteredFiles = new ArrayList<File>();
		for (File file : files) {
			for (String extension : extensions) {
				if (file.getName().endsWith(extension)) {
					filteredFiles.add(file);
					break;
				}
			}
		}
		return filteredFiles.toArray(new File[0]);
	}

	//returns the same list on an empty input
	public static File[] search(File[] files, String phrase) {
		if (phrase.equals("")) {
			return files;
		}

		List<File> searchedFiles = new ArrayList<File>();
		for (File file: files) {
			if (file.getName().contains(phrase)) {
				searchedFiles.add(file);
			}
		}
		return searchedFiles.toArray(new File[0]);
	}

	public static File[] sortByName(File[] files) {
		//make a clone of the file list in case
		//someone wants to keep the same base list
		File[] fileClone = files.clone();
		Comparator<File> comparator = new Comparator<File>() {
			@Override
			public int compare(File file, File file2) {
				return file.getName().compareTo(file2.getName());
			}
		};
		Arrays.sort(fileClone, comparator);
		return fileClone;
	}

	public File[] directoriesOnly(File[] files) {
		List<File> results = new ArrayList<File>();
		for (File file : files) {
			if (file.isDirectory()) {
				results.add(file);
			}
		}
		return results.toArray(new File[0]);
	}

	public File[] sortByDate(File[] files) {
		//also clone the file here, for the same reason described above
		File[] filesClone = files.clone();
		Comparator<File> comparator = new Comparator<File>() {
			@Override
			public int compare(File file, File file2) {
				return file.lastModified() > file2.lastModified() ? 1 : -1;
			}
		};
		Arrays.sort(filesClone, comparator);
		return filesClone;
	}

	public  File[] sortNewestOldest(File[] files) {
		List<File> tempList = Arrays.asList(sortByDate(files.clone()));
		Collections.reverse(tempList);
		return tempList.toArray(new File[0]);
	}
}
