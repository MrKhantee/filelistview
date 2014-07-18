package com.jcw.andriod.fileListView

import java.io.File


object ListUtils {
  //takes a list of files and a list of
  //accepted file types and filers them accordingly
  //returns the input list on empty input
  def filterExtensions(list: Array[File], fileEndings: Array[String]): Array[File] =
    if (fileEndings.length == 0) list
    else list filter (x => fileEndings exists (x == _))

  //returns the same list on an empty input
  def search(files: Array[File], searchPhrase: String): Array[File] =
    if (searchPhrase == "") files
    else files filter (_.getName().contains(searchPhrase))

  def sortByName(files: Array[File]): Array[File] =
    files sortWith ((x, y) => x.getName > y.getName)

  //oldest to newest
  def sortByDate(files: Array[File]): Array[File] =
    files sortWith ((x, y) => x.lastModified() > y.lastModified())

  def sortNewestToOldest(files: Array[File]): Array[File] =
    sortByDate(files).reverse


}
