package com.xored.clauncher.actions;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;

import com.xored.clauncher.CompositeLauncherTab;
import com.xored.clauncher.widgets.ItemList;

public class AddConfigAction extends AbstractConfigAction {
	private AddConfigAction(CompositeLauncherTab tab, ItemList available,
			ItemList selected) {
		super(tab, available, selected);
	}

	public static AddConfigAction getInstance(CompositeLauncherTab tab, ItemList available, ItemList selected) {
		return new AddConfigAction(tab, available, selected);
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		List selectedList = ((IStructuredSelection) available.getSelection()).toList();
		selected.data().addAll(selectedList);
		available.data().removeAll(selectedList);
		tab.update();
	}
}
