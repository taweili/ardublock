package com.ardublock;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class MainTest
{
	public static void main(String args[]) throws SAXException, IOException, ParserConfigurationException
	{
		MainTest test = new MainTest();
		test.setupTest();
		test.testFiles();
	}
	
	public void setupTest() throws SAXException, IOException, ParserConfigurationException
	{
		Main me = new Main();
		me.startArdublock();
	}
	
	public void testFiles() throws SAXException, IOException, ParserConfigurationException
	{
		
	}
}
