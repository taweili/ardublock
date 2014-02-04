package com.ardublock;

import java.io.File;
import java.io.IOException;
import java.net.URL;

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
		//test.teardown();
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
		String savedFiles[] = {"src/test/resources/examples/factorial.abp"};
		
		Context context = Context.getContext();
		
		for (String savedFile : savedFiles)
		{
			File file = new File(savedFile);
			context.loadArduBlockFile(file);
		}
		
	}
}
