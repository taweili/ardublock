package com.ardublock;

import java.io.IOException;
import java.util.Locale;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.ardublock.core.Starter;

public class Main
{
	public static void main(String args[]) throws SAXException, IOException, ParserConfigurationException
	{
		Locale.setDefault(new Locale("fr", "FR"));
		Starter starter = new Starter();
		starter.startArdublock();
	}
}
