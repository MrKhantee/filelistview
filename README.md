This is a file selecting list view for Android.

IMPORTANT: I highly recommend that you use the attached .jar as an attachable library to your project unless you already have the Scala compiler added to your project, as this project uses a few .scala files, so may well be incompatible with many projects in un-compiled form.

Usage:
    FileListView:
        This is a simple adaptation of the basic Android ListView to work as a file explorer. If you wish to heavily customise the UI of your file then this is probably what you should use.
        Important Fields: 
            setExtensions(String[] extensions) - this method lets you limit the possible file extensions that can be selected. Default: Empty List (accepts all extensions)
            setDirectoriesOnly(boolean enabled) - if this is set to true, then no files will be shown, only folders. Default: false
            searchWith(String text) - when this is set, only files/folders containing the specified text are shown. Default: "" (accepts all files/folders)
            setSortingMode(SortingMode mode) - this takes any of the available modes and sorts the list in the specified order. Default: Alphabetical
            refresh() - this refreshes the list adapter after **any one of the above fields is changed**. In **order to see the effects of the changes to the above files, you MUST call this method**.
            setFileSelectedListener(FileSelectedListener listener) - this is the listener for when a file is selected, but it should be noted that this listener **only fires on file selection** (i.e. **NOT on folder selection**)

    FileOpenView:
        This is what I would recommend using if you want to select an already created file. It is a set of views that includes the FileListView, an EditText to enable searching through the files and a Button to enable the selection of whole folders. 
        Important Fields: 
        
    FileSaveView:
        This is what I recommend using as a view if you want to save (a potentially new file) to the file system. Although it extends FileOpenView, not all of the methods are implemented, as some are not relevant. Those irrelevant methods throw exceptions when called
        Important Fields