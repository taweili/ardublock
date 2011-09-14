package com.ardublock.ui.listener;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.mit.blocks.renderable.RBHighlightHandler;
import edu.mit.blocks.renderable.RenderableBlock;
import edu.mit.blocks.workspace.Workspace;

public class TestListener implements ActionListener
{
	public void actionPerformed(ActionEvent e)
	{
		Workspace workspace = Workspace.getInstance();
		Iterable<RenderableBlock> rbs = workspace.getRenderableBlocks();
		for (RenderableBlock rb : rbs)
		{
			System.out.println(rb.getBlockID());
			RBHighlightHandler highlighter = new RBHighlightHandler(rb);
			if (rb.getBlock().getGenusName().equals("loop"))
			{
				rb.updateInSearchResults(true);
				System.out.println("highlighter: " + rb.getBlockID());
				///highlighter.setHighlightColor(Color.RED);
				//highlighter.setIsSearchResult(true);
				//highlighter.repaint();
				
			}
		}
	}

	
	//RBHighlightHandler
}
