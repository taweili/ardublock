package com.ardublock.ui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;

import javax.swing.JFileChooser;

import com.ardublock.core.Context;

import edu.mit.blocks.controller.WorkspaceController;
import edu.mit.blocks.workspace.Workspace;

public class SaveButtonListener implements ActionListener
{
	private Context context;
	public SaveButtonListener()
	{
		context = Context.getContext();
	}
	public void actionPerformed(ActionEvent e)
	{
		try
		{
			WorkspaceController workspaceController = context.getWorkspaceController();
			
			String saveString;
			//saveString = workspace.getSaveString();
			saveString = workspaceController.getSaveString();
			
			JFileChooser fileChooser = new JFileChooser();
			
			//How to get the ArdublockFrame?
			fileChooser.showSaveDialog(null);
			
			File saveFile = fileChooser.getSelectedFile();
			saveFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(saveFile);
			fos.write(saveString.getBytes());
			fos.flush();
			fos.close();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

}
