package com.ardublock.ui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;

import javax.swing.JFileChooser;

import edu.mit.blocks.controller.WorkspaceController;
import edu.mit.blocks.workspace.Workspace;

public class SaveButtonListener implements ActionListener
{
	private WorkspaceController wc;
	
	public SaveButtonListener(WorkspaceController wc)
	{
		this.wc = wc;
	}
	
	public void actionPerformed(ActionEvent e)
	{
		try
		{
			Workspace workspace = Workspace.getInstance();
			String saveString;
			//saveString = workspace.getSaveString();
			saveString = wc.getSaveString();
			
			
			JFileChooser fileChooser = new JFileChooser();
			
			fileChooser.showSaveDialog(workspace);
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
