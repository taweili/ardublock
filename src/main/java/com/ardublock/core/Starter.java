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
import com.ardublock.ui.OpenblocksFrame;

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
	public void startArdublock() throws SAXException, IOException, ParserConfigurationException
	{
		startOpenblocksFrame();
		startConsoleFrame();
	}
	
	private void startOpenblocksFrame() throws SAXException, IOException, ParserConfigurationException
	{
		
		OpenblocksFrame openblocksFrame = new OpenblocksFrame();
		openblocksFrame.setVisible(true);
	}
	
	private void startConsoleFrame()
	{
		ConsoleFrame consoleFrame = new ConsoleFrame();
		consoleFrame.setVisible(true);
	}
}