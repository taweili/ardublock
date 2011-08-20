package com.ardublock.ui.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Element;

import edu.mit.blocks.codeblocks.BlockConnectorShape;
import edu.mit.blocks.codeblocks.BlockGenus;
import edu.mit.blocks.codeblocks.BlockLinkChecker;
import edu.mit.blocks.codeblocks.CommandRule;
import edu.mit.blocks.codeblocks.SocketRule;
import edu.mit.blocks.controller.WorkspaceController;
import edu.mit.blocks.workspace.Workspace;

public class OpenButtonListener implements ActionListener
{
	private WorkspaceController wc;
	private Element lang;
	
	public OpenButtonListener(WorkspaceController wc, Element lang)
	{
		this.wc = wc;
		this.lang = lang;
	}
	
	public void actionPerformed(ActionEvent e)
	{
		try
		{
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.showOpenDialog(null);
			File savedFile = fileChooser.getSelectedFile();
			
			
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Element savedRoot = documentBuilder.parse(savedFile).getDocumentElement();
			
			wc.resetWorkspace();
			
			BlockConnectorShape.loadBlockConnectorShapes(lang);
			BlockGenus.loadBlockGenera(lang);
			BlockLinkChecker.addRule(new CommandRule());
			BlockLinkChecker.addRule(new SocketRule());
			
			Workspace workspace = Workspace.getInstance();
			
			workspace.loadWorkspaceFrom(null, lang);
			
			wc.loadProjectFromPath(savedFile.getAbsolutePath());
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
	}

}
