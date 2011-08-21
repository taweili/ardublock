package com.ardublock.core;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.ardublock.ui.ConsoleFrame;
import com.ardublock.ui.OpenblocksFrame;

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