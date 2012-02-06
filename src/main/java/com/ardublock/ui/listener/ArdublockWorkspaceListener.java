package com.ardublock.ui.listener;

import javax.swing.JFrame;

import com.ardublock.core.Context;
import com.ardublock.ui.OpenblocksFrame;

import edu.mit.blocks.workspace.WorkspaceEvent;
import edu.mit.blocks.workspace.WorkspaceListener;

public class ArdublockWorkspaceListener implements WorkspaceListener
{
	private Context context;
	private OpenblocksFrame frame;
	public ArdublockWorkspaceListener(OpenblocksFrame frame)
	{
		context = Context.getContext();
		this.frame = frame;
	}
	
	public void workspaceEventOccurred(WorkspaceEvent event)
	{
		if (!context.isWorkspaceChanged())
		{
			context.setWorkspaceChanged(true);
			String title = frame.makeFrameTitle();
			if (frame != null)
			{
				frame.setTitle(title);
			}
		}
		context.resetHightlightBlock();
	}
}
