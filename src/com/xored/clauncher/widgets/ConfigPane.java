package com.xored.clauncher.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class ConfigPane {
	private ItemList itemList;

	public ConfigPane(Composite root, String title, int offset){
		itemList = ItemList.getInstance(root);

		Label label = new Label(root, SWT.LEFT);
		label.setText(title);
		
		FormData labelLayout = new FormData();
		labelLayout.left = new FormAttachment(0+offset, 7);
		labelLayout.right = new FormAttachment(50+offset, -7);
		labelLayout.top = new FormAttachment(0, 7);
		label.setLayoutData(labelLayout);

		FormData listLayout = new FormData();
		listLayout.left = new FormAttachment(0+offset, 7);
		listLayout.right = new FormAttachment(50+offset, -7);
		listLayout.top = new FormAttachment(label, 7);
		listLayout.bottom = new FormAttachment(100, -7);
		itemList.getTable().setLayoutData(listLayout);
	}
	
	public static ConfigPane getInstance(Composite root, String title, int offset){
		return new ConfigPane(root, title, offset);
	}
	
	public ItemList itemList(){
		return itemList;
	}
}
