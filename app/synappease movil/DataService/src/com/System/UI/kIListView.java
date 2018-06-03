package com.System.UI;

import android.view.View;
import android.widget.AdapterView;

public abstract interface kIListView
{
	public abstract void listItemAction(kListView l, AdapterView<?> parent, View view, int position, long id);
	public abstract void doubleclick_listItemAction(kListView l, AdapterView<?> parent, View view, int position, long id);
}
