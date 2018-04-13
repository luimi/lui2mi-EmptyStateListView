# lui2mi-EmptyStateListView
Empty State ListView for Android

# instalation
for Gradle
```
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```
and
```
dependencies {
  implementation 'com.github.luimi:lui2mi-EmptyStateListView:1.2.1'
}
```

# implementation

Default list is GridView, but it automatically change to ExpandableListView or a custom list (Like RecyclerView or anything you want) 

# xml
```
xmlns:app="http://schemas.android.com/apk/res-auto"

<com.lui2mi.emptystate.EmptyStateList
        android:id="@+id/EmptyStateList"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
	app:title="Hello"
	app:text="This is a description, maximun 3 lines"
	app:image="@drawable/ic_launcher_background"
	app:columns="4">
</com.lui2mi.emptystate.EmptyStateList>
```

With custom list and custom emptystate

```
xmlns:app="http://schemas.android.com/apk/res-auto"

<com.lui2mi.emptystate.EmptyStateList
        android:id="@+id/EmptyStateList"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
	<com.lui2mi.emptystate.CustomEmptyState
            android:layout_width="match_parent"
            android:layout_height="match_parent">

            <!-- All stuff you want to add as emptystate -->
        </com.lui2mi.emptystate.CustomEmptyState>
	<com.lui2mi.emptystate.CustomList
            android:layout_width="match_parent"
            android:layout_height="match_parent">
            <!-- All stuff you want to add as list -->
        </com.lui2mi.emptystate.CustomList>
</com.lui2mi.emptystate.EmptyStateList>
```
CustomEmptyState and CustomList act like a linear layout, so you can add anything.
It's not necesary to add both, you can use one. 

# java

EmptyStateList emptyStateList;
emptyStateList = new EmptyStateList(context);
emptyStateList.setAdapter(/*ListAdapter (any Adapter) or BaseExpandableListAdapter*/);
emptyStateList.addCustomListView(View);	//add any view to the custom list area
emptyStateList.setIsEmpty(boolean);	//set and swap between Emptystate and list
emptyStateList.getGridView();		//returns the GridView view
emptyStateList.getExpandableListView();	//returns the ExpandableListView view
emptyStateList.showTopProgress();	//display the top progressbar(bar)
emptyStateList.showCenterProgress();	//display the center progressbar(Circle), only works on default empty state.
emptyStateList.hideTopProgress();	//hide the top progressbar
emptyStateList.hideCenterProgress();	//hide the center progressbar
emptyStateList.setTitle(String);	//Set the title of the default emptyState
emptyStateList.setText(String);		//Set the text of the default emptyState

