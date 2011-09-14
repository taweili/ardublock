package com.ardublock.ui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.ardublock.core.Context;

import edu.mit.blocks.controller.WorkspaceController;

public class OpenButtonListener implements ActionListener
{
	private Context context;
	private JFileChooser fileChooser;
	private JFrame parentFrame;
	
	public OpenButtonListener(JFrame frame)
	{
		context = Context.getContext();
		fileChooser = new JFileChooser();
		this.parentFrame = frame;
	}
	
	public void actionPerformed(ActionEvent e)
	{
		System.out.println("open action p");
		if (context.isWorkspaceChanged())
		{
			int optionValue = JOptionPane.showOptionDialog(parentFrame, "over", "question", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, JOptionPane.NO_OPTION);
			if (optionValue == JOptionPane.YES_OPTION)
			{
				loadFile();
			}
		}
		else
		{
			loadFile();
		}
	}
		
	private void loadFile()
	{
		fileChooser.showOpenDialog(parentFrame);
		File savedFile = fileChooser.getSelectedFile();
		if (savedFile != null)
		{
			String saveFilePath = savedFile.getAbsolutePath();
			WorkspaceController workspaceController = context.getWorkspaceController();
			workspaceController.resetWorkspace();
			workspaceController.loadProjectFromPath(saveFilePath);
			context.setSaveFilePath(saveFilePath);
			context.setWorkspaceChanged(false);
			if (parentFrame != null)
			{
				parentFrame.setTitle(context.makeFrameTitle());
			}
		}
	}

}
