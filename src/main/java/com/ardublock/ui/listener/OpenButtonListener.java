package com.ardublock.ui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import com.ardublock.core.Context;

import edu.mit.blocks.controller.WorkspaceController;

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
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.showOpenDialog(null);
			File savedFile = fileChooser.getSelectedFile();
			if (savedFile != null)
			{
				WorkspaceController workspaceController = context.getWorkspaceController();
				workspaceController.resetWorkspace();
				//workspaceController.loadFreshWorkspace();
				workspaceController.loadProjectFromPath(savedFile.getAbsolutePath());
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
	}

}
