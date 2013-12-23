package com.ardublock.ui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import com.ardublock.core.Context;
import com.ardublock.ui.OpenblocksFrame;

import edu.mit.blocks.controller.WorkspaceController;

public class SaveAsButtonListener implements ActionListener
{
	private Context context;
	private OpenblocksFrame parentFrame;
	public SaveAsButtonListener(OpenblocksFrame frame)
	{
		context = Context.getContext();
		parentFrame = frame;
	}
	
	public void actionPerformed(ActionEvent e)
	{
		parentFrame.doSaveAsArduBlockFile();
	}


}
