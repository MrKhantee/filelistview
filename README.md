IMPORTANT -- Please DO NOT USE THESE FILES yet -- I have not even started the process of debugging it,
so it is likely to crash (on github for testing reasons only).


This is a file selecting list view for Android, a few other views are also included to make the use of the library easier.

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
            setSearchEnabled(boolean enabled) - this method lets you set whether a search bar will appear at that top of the view to let you search through the current selected directory. Default: true
            setFolderSelectEnabled(boolean enabled) - when set to true, a button will appear next to the search bar to enable the selection of the current folder (which will fire the FileSelectedListener upon being pressed). Default: false
            setFileSelectedListener(FileSelectedListener listener) - this is a listener for when a file is selected... see FileListView.setFileSelectedListener(..)
        
    FileSaveView:
        This is what I recommend using as a view if you want to save (a potentially new file) to the file system. Although it extends FileOpenView, not all of the methods are implemented, as some are not relevant. Those irrelevant methods throw exceptions when called
        Important Fields:
            enableOverwriteWarning(boolean enabled) - when set to true, a warning dialog will be displayed before the selection listener is fired to make sure the user knows they are replacing a file. When false, this dialog will not be shown. Default: true
            setFileSelectedListener(FileSelectedListener listener) - this is fired when the Save File button is pressed, and sends the name typed into the EditText and the base directory back as a single file. 
            setSearchEnabled(boolean enabled) - throws a NoSuchMethodException -- do not use
            setFolderSelectEnabled(boolean enabled - throws a NoSuchMethodException -- do not use
        

Contributing:
    I will eagerly welcome all contributions, if you are looking for something to contribute to, see the todo list below.
    
Current Todo List:
    More Documentation (both in this file and around each method)
    File options -- possibly a context menu to open on long press of FileListViewItem to enable things like copying, moving, renaming etc..