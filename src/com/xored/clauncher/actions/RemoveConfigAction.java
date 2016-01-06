package com.xored.clauncher.actions;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;

import com.xored.clauncher.CompositeLauncherTab;
import com.xored.clauncher.widgets.ItemList;

public class RemoveConfigAction extends AbstractConfigAction {
	private List<String> allConfigs;

	private RemoveConfigAction(CompositeLauncherTab tab, ItemList available,
			ItemList selected, List<String> allConfigs) {
		super(tab, available, selected);
		this.allConfigs = allConfigs;
	}

	public static RemoveConfigAction getInstance(CompositeLauncherTab tab,
			ItemList available, ItemList selected, List<String> allConfigs) {
		return new RemoveConfigAction(tab, available, selected, allConfigs);
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		selected.data().removeAll(((IStructuredSelection) selected.getSelection()).toList());
		available.data().clear();
		allConfigs.stream().filter(e -> !selected.data().contains(e)).forEach(e -> available.data().add(e));
		tab.update();
	}
}
