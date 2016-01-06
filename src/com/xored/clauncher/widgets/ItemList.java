package com.xored.clauncher.widgets;

import java.util.ArrayList;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class ItemList extends TableViewer{
    private final IObservableList data = new WritableList(new ArrayList<>(), String.class);

	public static ItemList getInstance(Composite parent){
		return new ItemList(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.SINGLE);
	}
	
	private ItemList(Composite parent, int style) {
		super(parent, style);
	    setContentProvider(new ObservableListContentProvider());
	    setInput(data);
	}
	
	public IObservableList data(){
		return data;
	}
}
