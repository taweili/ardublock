package com.ardublock.ui;

import java.awt.Dimension;

import javax.swing.JFrame;

import org.w3c.dom.Element;

import com.ardublock.core.Context;

import edu.mit.blocks.codeblocks.BlockConnectorShape;
import edu.mit.blocks.codeblocks.BlockGenus;
import edu.mit.blocks.codeblocks.BlockLinkChecker;
import edu.mit.blocks.codeblocks.CommandRule;
import edu.mit.blocks.codeblocks.SocketRule;
import edu.mit.blocks.workspace.Page;
import edu.mit.blocks.workspace.Workspace;

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
		
		//I don't know why the code blow doesn't work.
		//It says cannot find dtd file -_-b
		/*
		workspaceController.resetWorkspace();
		workspaceController.setLangDefFilePath(context.getArdublockLangPath());
		workspaceController.loadFreshWorkspace();
		*/
		
		//JComponent workspace = workspaceController.getWorkspacePanel();
		
		//So I change it startup code to blow
		Element ardublockLangElement = context.getArdublockLangRoot();
		Workspace workspace = Workspace.getInstance();
		workspace.reset();
		/* MUST load shapes before genuses in order to initialize
		connectors within each block correctly */
		BlockConnectorShape.loadBlockConnectorShapes(ardublockLangElement);
		
		//load genuses
		BlockGenus.loadBlockGenera(ardublockLangElement);
		
		//load rules
		BlockLinkChecker.addRule(new CommandRule());
		BlockLinkChecker.addRule(new SocketRule());

		workspace.loadWorkspaceFrom(null, ardublockLangElement);
		
		//chagne default page from Turtles to Ardublock
		Page turtlesPage = workspace.getPageNamed("Turtles");
		workspace.removePage(turtlesPage);
		
		Page ardublockPage = context.getArdublockPage();
		workspace.addPage(ardublockPage);
		this.add(workspace);
	}
}
