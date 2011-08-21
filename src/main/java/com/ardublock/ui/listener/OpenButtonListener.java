package com.ardublock.ui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Element;

import com.ardublock.core.Context;

import edu.mit.blocks.codeblocks.BlockConnectorShape;
import edu.mit.blocks.codeblocks.BlockGenus;
import edu.mit.blocks.codeblocks.BlockLinkChecker;
import edu.mit.blocks.codeblocks.CommandRule;
import edu.mit.blocks.codeblocks.SocketRule;
import edu.mit.blocks.controller.WorkspaceController;
import edu.mit.blocks.workspace.Workspace;

public class OpenButtonListener implements ActionListener
{
	private Context context;
	public OpenButtonListener()
	{
		context = Context.getContext();
	}
	
	public void actionPerformed(ActionEvent e)
	{
		try
		{
			WorkspaceController workspaceController = context.getWorkspaceController();
			
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.showOpenDialog(null);
			File savedFile = fileChooser.getSelectedFile();
			
			

			workspaceController.loadProject(savedFile.getAbsolutePath(), context.getArdublockLangPath());
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
	}

}
