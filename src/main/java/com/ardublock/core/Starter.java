package com.ardublock.core;

import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JFrame;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.ardublock.ui.ConsoleFrame;

import edu.mit.blocks.codeblocks.BlockConnectorShape;
import edu.mit.blocks.codeblocks.BlockGenus;
import edu.mit.blocks.codeblocks.BlockLinkChecker;
import edu.mit.blocks.codeblocks.CommandRule;
import edu.mit.blocks.codeblocks.SocketRule;
import edu.mit.blocks.controller.WorkspaceController;
import edu.mit.blocks.workspace.Page;
import edu.mit.blocks.workspace.Workspace;

public class Starter
{
	private Element ardublockLang;
	
	private WorkspaceController wc;
	
	public void startArdublock() throws SAXException, IOException, ParserConfigurationException
	{
		startOpenblocksFrame();
		startConsoleFrame();
	}
	
	private void startOpenblocksFrame() throws SAXException, IOException, ParserConfigurationException
	{
		wc = new WorkspaceController();
		
		Workspace workspace = Workspace.getInstance();
		
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		ardublockLang = documentBuilder.parse(getClass().getResource("/com/ardublock/block/ardublock_def.xml").toString()).getDocumentElement();
		
		JFrame jframe = new JFrame("Ardublock");
		jframe.setSize(new Dimension(640, 480));
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		/* MUST load shapes before genuses in order to initialize
        connectors within each block correctly */
       BlockConnectorShape.loadBlockConnectorShapes(ardublockLang);

       //load genuses
       BlockGenus.loadBlockGenera(ardublockLang);

       //load rules
       BlockLinkChecker.addRule(new CommandRule());
       BlockLinkChecker.addRule(new SocketRule());
       
       workspace.loadWorkspaceFrom(null, ardublockLang);
       
       Page defaultPage = workspace.getPageNamed("Turtles");
       
       workspace.removePage(defaultPage);
       
       Page ardublockPage = new Page("Ardublock");
       workspace.addPage(ardublockPage);
		
       jframe.add(workspace);
       
       jframe.setVisible(true);
	}
	
	private void startConsoleFrame()
	{
		ConsoleFrame consoleFrame = new ConsoleFrame(wc, ardublockLang);
		consoleFrame.setVisible(true);
	}
}