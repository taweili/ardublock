package com.ardublock;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.ardublock.core.Context;

public class MainTest
{
	private Main main;

	public static void main(String args[]) throws SAXException, IOException, ParserConfigurationException
	{
		MainTest test = new MainTest();
		test.setupTest();
		test.testFiles();
		test.teardown();
	}
	
	private void teardown() {
		main.shutdown();
	}

	public void setupTest() throws SAXException, IOException, ParserConfigurationException
	{
		main = new Main();
		main.startArdublock();
		
	}
	
	public void testFiles() throws SAXException, IOException, ParserConfigurationException
	{
		String savedFiles[] = {"factorial.abp"};
		
		for (String savedFile : savedFiles)
		{
			System.out.println(savedFile);
		}
		
		Context context = Context.getContext();
		//context.loadArduBlockFile();
	}
}
