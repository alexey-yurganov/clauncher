package com.xored.clauncher.actions;

import org.eclipse.jface.viewers.ISelectionChangedListener;

import com.xored.clauncher.CompositeLauncherTab;
import com.xored.clauncher.widgets.ItemList;

public abstract class AbstractConfigAction implements ISelectionChangedListener {
	protected ItemList available;
	protected ItemList selected;
	protected CompositeLauncherTab tab;
	
	protected AbstractConfigAction(CompositeLauncherTab tab, ItemList available, ItemList selected) {
		this.available = available;
		this.tab = tab;
		this.selected = selected;
	}
}
