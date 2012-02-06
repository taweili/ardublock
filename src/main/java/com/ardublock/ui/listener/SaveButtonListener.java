package com.ardublock.ui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import com.ardublock.core.Context;

import edu.mit.blocks.controller.WorkspaceController;

public class SaveButtonListener implements ActionListener
{
	private Context context;
	private JFileChooser fileChooser;
	private JFrame parentFrame;
	public SaveButtonListener(JFrame frame)
	{
		context = Context.getContext();
		fileChooser = new JFileChooser();
		parentFrame = frame;
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if (context.isWorkspaceChanged())
		{
			try
			{
				WorkspaceController workspaceController = context.getWorkspaceController();
				String saveString = workspaceController.getSaveString();
				
				String saveFilePath = context.getSaveFilePath();
				if (saveFilePath == null)
				{
					int chooseResult;
					chooseResult = fileChooser.showSaveDialog(parentFrame);
					if (chooseResult == JFileChooser.APPROVE_OPTION)
					{
						File saveFile = fileChooser.getSelectedFile();
						if (saveFile != null)
						{
							saveFile(saveFile, saveString);
							saveFilePath = saveFile.getAbsolutePath();
							context.setSaveFilePath(saveFilePath);
							context.setWorkspaceChanged(false);
							if (parentFrame != null)
							{
								parentFrame.setTitle(context.makeFrameTitle());
							}
							context.didSave();
						}
					}
				}
				else
				{
					File saveFile = new File(saveFilePath);
					saveFile(saveFile, saveString);
					context.setWorkspaceChanged(false);
					if (parentFrame != null)
					{
						parentFrame.setTitle(context.makeFrameTitle());
					}
					context.didSave();
				}
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}

	private void saveFile(File saveFile, String saveString) throws IOException
	{
		if (!saveFile.exists())
		{
			saveFile.createNewFile();
		}
		FileOutputStream fos = new FileOutputStream(saveFile);
		fos.write(saveString.getBytes("UTF8"));
		fos.flush();
		fos.close();
	}
}
