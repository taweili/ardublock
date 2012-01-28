package com.ardublock.ui;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;

import edu.mit.blocks.workspace.Workspace;

import com.ardublock.core.Context;
import com.ardublock.ui.listener.ArdublockWorkspaceListener;
import com.ardublock.ui.listener.GenerateCodeButtonListener;
import com.ardublock.ui.listener.OpenButtonListener;
import com.ardublock.ui.listener.SaveButtonListener;
import com.ardublock.ui.listener.OpenblocksFrameListener;


public class OpenblocksFrame extends JFrame
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
	
	public OpenblocksFrame()
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
		
		/*
		WorkspaceController workspaceController = context.getWorkspaceController();
		JComponent workspaceComponent = workspaceController.getWorkspacePanel();
		*/
		
		Workspace workspace = context.getWorkspace();
		
		// WTF I can't add worksapcelistener by workspace contrller
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
		this.add(workspace, BorderLayout.CENTER);
	}
}
