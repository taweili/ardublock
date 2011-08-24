package com.ardublock.ui;

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JFrame;

import com.ardublock.core.Context;

import edu.mit.blocks.controller.WorkspaceController;

public class OpenblocksFrame extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2841155965906223806L;

	public OpenblocksFrame()
	{
		this.setTitle("Ardublock");
		this.setSize(new Dimension(800, 600));
		//put the frame to the center of screen
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		initOpenBlocks();
	}
	
	private void initOpenBlocks()
	{
		Context context = Context.getContext();
		
		WorkspaceController workspaceController = context.getWorkspaceController();
		JComponent workspace = workspaceController.getWorkspacePanel();
		
		this.add(workspace);
		
	}
}
