package com.ardublock.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public class PropertiesReader
{
	public static Map<String, PropertiesReader> propertiesReaderMap = new SafeHashMap<String, PropertiesReader>();
	public static final String DEFAULT_PROPERTIES_FILE = "heqichen.properties";
	
	private String filename;
	private Properties p;
	
	private PropertiesReader(String filename)
	{
		this.filename = filename; 
		InputStream is = PropertiesReader.class.getClassLoader().getResourceAsStream(this.filename);
		if (is == null)
		{
			System.out.println("no file found: " + this.filename);
		}
		p = new Properties();
		try
		{
			p.load(is);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String getValue(String key)
	{
		return getValue(key, DEFAULT_PROPERTIES_FILE);
	}
	
	public static String getValue(String key, String file)
	{
		PropertiesReader propertiesReader = PropertiesReader.getPropertiesReader(file);
		return propertiesReader.readValue(key);
	}
	
	private String readValue(String key)
	{
		return p.getProperty(key);
	}
	
	private static PropertiesReader getPropertiesReader(String filename)
	{
		PropertiesReader propertiesReader;
		propertiesReader = propertiesReaderMap.get(filename);
		if (propertiesReader == null)
		{
			synchronized (PropertiesReader.class)
			{
				propertiesReader = propertiesReaderMap.get(filename);
				if (propertiesReader == null)
				{
					propertiesReader = new PropertiesReader(filename);
					propertiesReaderMap.put(filename, propertiesReader);
				}
			}
		}
		return propertiesReader;
	}
}
