package com.ardublock.util.translator;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

public class PropReader
{
	private LinkedProperties sourceProp;
	private LinkedProperties targetProp;
	
	public static void main(String args[]) throws IOException
	{
		PropReader me = new PropReader();
		me.loadProperites();
		me.retrieveSourceProperites();
	}

	private void retrieveSourceProperites()
	{
		Enumeration e = sourceProp.keys();
		while (e.hasMoreElements())
		{
			String key = (String) e.nextElement();
			String value = sourceProp.getProperty(key);
			if (targetProp.containsKey(key))
			{
				value = targetProp.getProperty(key);
			}
			
			System.out.println(String.format("%s=%s", key, value));
			
		}
		
	}

	private void loadProperites() throws IOException
	{
		InputStream sis = this.getClass().getClassLoader().getResourceAsStream("com/ardublock/block/ardublock.properties");
		sourceProp = new LinkedProperties();
		sourceProp.load(sis);
		
		InputStream tis = this.getClass().getClassLoader().getResourceAsStream("com/ardublock/block/ardublock_zh_CN.properties");
		targetProp = new LinkedProperties();
		targetProp.load(tis);
	}
}
