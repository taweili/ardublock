package com.ardublock.ui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.ardublock.core.Context;
import com.ardublock.ui.OpenblocksFrame;

import edu.mit.blocks.controller.WorkspaceController;

public class OpenButtonListener implements ActionListener
{
	private Context context;
	
	private OpenblocksFrame parentFrame;
	
	public OpenButtonListener(OpenblocksFrame frame)
	{
		context = Context.getContext();
		
		this.parentFrame = frame;
	}
	
	public void actionPerformed(ActionEvent e)
	{
		parentFrame.doOpenArduBlockFile();
	}

}
