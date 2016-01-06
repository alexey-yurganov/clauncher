package com.xored.clauncher;

import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;

public class CompositeLauncherDelegate implements ILaunchConfigurationDelegate  {
	@Override
	public void launch(ILaunchConfiguration config, String mode, ILaunch session, IProgressMonitor monitor) throws CoreException {
		List<String> selectedConfiguartionNames = config.getAttribute(CompositeLauncherTab.SELECTED_CONFIGURATIONS_NAMES_KEY, Collections.emptyList());

		for(ILaunchConfiguration lc : DebugPlugin.getDefault().getLaunchManager().getLaunchConfigurations())
			launch(lc, mode, monitor, selectedConfiguartionNames.contains(lc.getName()));
	}
	
	private void launch(ILaunchConfiguration lc, String mode, IProgressMonitor monitor, boolean shouldBeLaunched) throws CoreException{
		if(!shouldBeLaunched)
			return;
		lc.launch(mode, monitor);
	}
}
