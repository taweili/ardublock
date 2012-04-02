package com.ardublock.ui;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import edu.mit.blocks.controller.WorkspaceController;
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
	private String saveFilePath;
	private String saveFileName;
	private JFileChooser fileChooser;
	private FileFilter ffilter;
	
	private ResourceBundle uiMessageBundle;
	
	public void addListener(OpenblocksFrameListener ofl)
	{
		context.registerOpenblocksFrameListener(ofl);
	}
	
	public String makeFrameTitle()
	{
		String title = Context.APP_NAME + " " + saveFileName;
		if (context.isWorkspaceChanged())
		{
			title = title + " *";
		}
		return title;
		
	}
	
	public OpenblocksFrame()
	{
		saveFilePath = null;
		saveFileName = "untitled";
		
		context = Context.getContext();
		this.setTitle(makeFrameTitle());
		this.setSize(new Dimension(800, 600));
		this.setLayout(new BorderLayout());
		//put the frame to the center of screen
		this.setLocationRelativeTo(null);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		uiMessageBundle = ResourceBundle.getBundle("com.ardublock.ui");
		
		fileChooser = new JFileChooser();
		ffilter = new FileNameExtensionFilter(uiMessageBundle.getString("ardublock.file.suffix"), "abp");
		fileChooser.setFileFilter(ffilter);
		fileChooser.addChoosableFileFilter(ffilter);
		
		
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
		JButton saveButton = new JButton(uiMessageBundle.getString("ardublock.ui.save"));
		saveButton.addActionListener(new SaveButtonListener(this));
		JButton openButton = new JButton(uiMessageBundle.getString("ardublock.ui.load"));
		openButton.addActionListener(new OpenButtonListener(this));
		JButton generateButton = new JButton(uiMessageBundle.getString("ardublock.ui.upload"));
		generateButton.addActionListener(new GenerateCodeButtonListener(this, context));
		

		buttons.add(saveButton);
		buttons.add(openButton);
		buttons.add(generateButton);
		
		this.add(buttons, BorderLayout.NORTH);
		this.add(workspace, BorderLayout.CENTER);
	}
	
	public void doOpenArduBlockFile()
	{
		if (context.isWorkspaceChanged())
		{
			int optionValue = JOptionPane.showOptionDialog(this, uiMessageBundle.getString("message.content.open_unsaved"), uiMessageBundle.getString("message.title.question"), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, JOptionPane.YES_OPTION);
			if (optionValue == JOptionPane.YES_OPTION)
			{
				doSaveArduBlockFile();
				this.loadFile();
			}
			else
			{
				if (optionValue == JOptionPane.NO_OPTION)
				{
					this.loadFile();
				}
			}
		}
		else
		{
			this.loadFile();
		}
		this.setTitle(makeFrameTitle());
	}
	
	private void loadFile()
	{
		int result = fileChooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION)
		{
			File savedFile = fileChooser.getSelectedFile();
			if (!savedFile.exists())
			{
				JOptionPane.showOptionDialog(this, uiMessageBundle.getString("message.file_not_found"), uiMessageBundle.getString("message.title.error"), JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, null, JOptionPane.OK_OPTION);
				return ;
			}
			
			saveFilePath = savedFile.getAbsolutePath();
			saveFileName = savedFile.getName();
			try
			{
				context.loadArduBlockFile(savedFile);
				context.setWorkspaceChanged(false);
			}
			catch (IOException e)
			{
				JOptionPane.showOptionDialog(this, uiMessageBundle.getString("message.file_not_found"), uiMessageBundle.getString("message.title.error"), JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, null, JOptionPane.OK_OPTION);
				e.printStackTrace();
			}
		}
	}
	
	public void doSaveArduBlockFile()
	{
		if (context.isWorkspaceChanged())
		{
			try
			{
				WorkspaceController workspaceController = context.getWorkspaceController();
				String saveString = workspaceController.getSaveString();
				
				if (saveFilePath == null)
				{
					int chooseResult;
					chooseResult = fileChooser.showSaveDialog(this);
					if (chooseResult == JFileChooser.APPROVE_OPTION)
					{
						File saveFile = fileChooser.getSelectedFile();
						saveFile = checkFileSuffix(saveFile);
						if (saveFile != null)
						{
							if (saveFile.exists())
							{
								int optionValue = JOptionPane.showOptionDialog(this, uiMessageBundle.getString("message.content.overwrite"), uiMessageBundle.getString("message.title.question"), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, JOptionPane.YES_OPTION);
								if (optionValue != JOptionPane.YES_OPTION)
								{
									return ;
								}
							}
							context.saveArduBlockFile(saveFile, saveString);
							saveFilePath = saveFile.getAbsolutePath();
							saveFileName = saveFile.getName();
							context.setWorkspaceChanged(false);
							this.setTitle(this.makeFrameTitle());
							
						}
					}
				}
				else
				{
					File saveFile = new File(saveFilePath);
					context.saveArduBlockFile(saveFile, saveString);
					saveFilePath = saveFile.getAbsolutePath();
					saveFileName = saveFile.getName();
					context.setWorkspaceChanged(false);
					this.setTitle(this.makeFrameTitle());
					
				}
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}
	
	private File checkFileSuffix(File saveFile)
	{
		String filePath = saveFile.getAbsolutePath();
		if (filePath.endsWith(".abp"))
		{
			return saveFile;
		}
		else
		{
			return new File(filePath + ".abp");
		}
	}
}
