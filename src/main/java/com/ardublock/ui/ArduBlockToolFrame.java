package com.ardublock.ui;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;


import com.ardublock.core.Context;

import com.ardublock.ui.listener.ArdublockWorkspaceListener;
import com.ardublock.ui.listener.GenerateCodeButtonListener;
import com.ardublock.ui.listener.OpenButtonListener;
import com.ardublock.ui.listener.SaveButtonListener;
import com.ardublock.ui.listener.OpenblocksFrameListener;

import edu.mit.blocks.controller.WorkspaceController;
import edu.mit.blocks.workspace.Workspace;

public class ArduBlockToolFrame extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2841155965906223806L;

	private Context context;
	
	public void addListener(OpenblocksFrameListener ofl)
	{
		context.registerOpenblocksFrameListener(ofl);
	}
	
	public ArduBlockToolFrame()
	{
		context = Context.getContext();
		this.setTitle(context.makeFrameTitle());
		this.setSize(new Dimension(800, 600));
		this.setLayout(new BorderLayout());
		//put the frame to the center of screen
		this.setLocationRelativeTo(null);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		initOpenBlocks();
	}
	
	private void initOpenBlocks()
	{
		Context context = Context.getContext();
		
		WorkspaceController workspaceController = context.getWorkspaceController();
		JComponent workspaceComponent = workspaceController.getWorkspacePanel();
		
		//WTF I can't add worksapcelistener by workspace contrller
		Workspace workspace = new Workspace();
		workspace.addWorkspaceListener(new ArdublockWorkspaceListener(this));
		
		JPanel buttons = new JPanel();
		buttons.setLayout(new FlowLayout());
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new SaveButtonListener(this));
		JButton openButton = new JButton("Load");
		openButton.addActionListener(new OpenButtonListener(this));
		JButton generateButton = new JButton("Upload");
		generateButton.addActionListener(new GenerateCodeButtonListener(this, context));

		buttons.add(saveButton);
		buttons.add(openButton);
		buttons.add(generateButton);

		this.add(buttons, BorderLayout.NORTH);
		this.add(workspaceComponent, BorderLayout.CENTER);
	}
}
