package com.ardublock.ui.listener;

import com.ardublock.core.Context;
import com.ardublock.ui.OpenblocksFrame;

import edu.mit.blocks.codeblocks.Block;
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
		//System.out.println("Event: " + event.getEventType());
		if (!context.isWorkspaceChanged())
		{
			context.setWorkspaceChanged(true);
			context.setWorkspaceEmpty(false);
			String title = frame.makeFrameTitle();
			if (frame != null)
			{
				frame.setTitle(title);
			}
		}
		context.resetHightlightBlock();
		
		
		Iterable<Block> blocks = context.getWorkspace().getBlocks();
		for (Block block : blocks)
		{
			//System.out.println(block.getBlockID() + ": " + block.getBlockLabel());
		}
		//System.out.println("======================\n\n");
		
	}
}
