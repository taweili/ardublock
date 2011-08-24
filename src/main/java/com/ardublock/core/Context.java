package com.ardublock.core;

import edu.mit.blocks.controller.WorkspaceController;

public class Context
{
	private static Context singletonContext;
	
	public static Context getContext()
	{
		if (singletonContext == null)
		{
			synchronized (Context.class)
			{
				if (singletonContext == null)
				{
					singletonContext = new Context();
				}
			}
		}
		return singletonContext;
	}
	
	private WorkspaceController workspaceController;
	
	private Context() 
	{
		workspaceController = new WorkspaceController();
		workspaceController.resetWorkspace();
		workspaceController.resetLanguage();
		workspaceController.setLangDefDtd(this.getClass().getResourceAsStream(Configuration.LANG_DTD_PATH));
		workspaceController.setLangDefStream(this.getClass().getResourceAsStream(Configuration.ARDUBLOCK_LANG_PATH));
		workspaceController.loadFreshWorkspace();
	}

	public WorkspaceController getWorkspaceController() {
		return workspaceController;
	}
}
