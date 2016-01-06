package com.xored.clauncher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.IDebugModelPresentation;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;

import com.xored.clauncher.actions.AddConfigAction;
import com.xored.clauncher.actions.RemoveConfigAction;
import com.xored.clauncher.widgets.ConfigPane;
import com.xored.clauncher.widgets.ItemList;

public class CompositeLauncherTab extends AbstractLaunchConfigurationTab {
	public static final String SELECTED_CONFIGURATIONS_NAMES_KEY = "configurations",
							   TAB_NAME = "Launch configuarations",
							   AVAILABLE_CONFIGURATIONS = "Available launch configuartions",
							   SELECTED_CONFIGURATIONS = "Selected launch configuartions";

	private ItemList availableConfigs;
	private ItemList selectedConfigs;
	private List<String> allConfigs = new ArrayList<>();

	@Override
	public void createControl(Composite parent) {
		final Composite root = new Composite(parent, SWT.FILL);
		root.setLayout(new FormLayout());
		
		selectedConfigs = ConfigPane.getInstance(root, SELECTED_CONFIGURATIONS, 0).itemList();
		availableConfigs = ConfigPane.getInstance(root, AVAILABLE_CONFIGURATIONS, 50).itemList();
				
		availableConfigs.addSelectionChangedListener(AddConfigAction.getInstance(this, availableConfigs, selectedConfigs));
		selectedConfigs.addSelectionChangedListener(RemoveConfigAction.getInstance(this, availableConfigs, selectedConfigs, allConfigs));

		decorateIcons();
		setControl(root);
	}

	public void update(){
		updateLaunchConfigurationDialog();
	}

	@Override
	public String getName() {
		return TAB_NAME;
	}

	@Override
	public void initializeFrom(ILaunchConfiguration config) {
		List<String> selectedList = resolveSelectedConfigurationList(config);

		allConfigs.clear();
		availableConfigs.data().clear();
		
		for (ILaunchConfiguration c : resolveLaunchConfigurations()) {
			String configurationName = c.getName();

			if (!configurationName.equals(config.getName())) {
				allConfigs.add(configurationName);

				if (!selectedList.contains(configurationName)) {
					availableConfigs.data().add(configurationName);
				}
			}
		}

		selectedConfigs.data().clear();
		
		for (String s : selectedList) 
			selectedConfigs.data().add(s);

		update();
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy config) {
		config.setAttribute(SELECTED_CONFIGURATIONS_NAMES_KEY, selectedConfigs.data());
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy arg0) {}
	
	private void decorateIcons() {
		IDebugModelPresentation debugModelPresentation = DebugUITools.newDebugModelPresentation();
		Map<String, Image> imageByConfigName = new HashMap<>();

			for (ILaunchConfiguration conf : resolveLaunchConfigurations())
				imageByConfigName.put(conf.getName(), debugModelPresentation.getImage(conf));

		availableConfigs.setLabelProvider(labelProvider(imageByConfigName));
		selectedConfigs.setLabelProvider(labelProvider(imageByConfigName));
	}

	private List<ILaunchConfiguration> resolveLaunchConfigurations(){
		try {
			return Arrays.asList(getLaunchManager().getLaunchConfigurations());
		} catch (CoreException e) {
			setErrorMessage(e.getMessage());
		}
		
		return new ArrayList<ILaunchConfiguration>();
	}
	
	private List<String> resolveSelectedConfigurationList(ILaunchConfiguration config){
		List<String> selectedList = new ArrayList<>();
		try {
			selectedList = config.getAttribute(SELECTED_CONFIGURATIONS_NAMES_KEY, Collections.emptyList());
		} catch (CoreException e) {
			setErrorMessage(e.getMessage());
		}
		
		return selectedList;
	}

	private LabelProvider labelProvider(Map<String, Image> imageByConfigName){
		return new LabelProvider() {
			@Override
			public Image getImage(Object element) {
				return imageByConfigName.get(element.toString());
			}
		};
	}
}
