package com.ardublock.ui;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;


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
		this.setTitle("ArduBlock");
		this.setSize(new Dimension(800, 600));
		this.setLayout(new BorderLayout());
		//put the frame to the center of screen
		this.setLocationRelativeTo(null);
		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initOpenBlocks();
	}
	
	private void initOpenBlocks()
	{
		Context context = Context.getContext();
		
		WorkspaceController workspaceController = context.getWorkspaceController();
		JComponent workspace = workspaceController.getWorkspacePanel();
		JPanel buttons = new JPanel();
		buttons.setLayout(new FlowLayout());
		buttons.add(new JButton("Save"));
		buttons.add(new JButton("Load"));
		buttons.add(new JButton("Generate"));
		
		this.add(buttons, BorderLayout.NORTH);
		this.add(workspace, BorderLayout.CENTER);
	}
	
	public void didSave() {
	}
	
	public void didLoad() {
	}
	
	public void didGenerate() {
	}
}
