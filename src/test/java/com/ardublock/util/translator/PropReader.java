package com.ardublock.util.translator;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class PropReader
{
	private LinkedProperties sourceProp;
	private LinkedProperties targetProp;
	
	public static void main(String args[]) throws IOException
	{
		PropReader me = new PropReader();
		me.loadProperites("com/ardublock/block/ardublock.properties", "com/ardublock/block/ardublock_zh_CN.properties");
		//me.loadProperites("translate/ardublock.properties", "com/ardublock/block/ardublock_zh_CN.properties");
		me.retrieveSourceProperites();
	}

	private void retrieveSourceProperites()
	{
		Enumeration e = sourceProp.keys();
		
		List<String> list = Collections.list(e);
        Collections.sort(list);
 
        //
        // Prints out all generated number after sorted.
        //
        for (String key : list)
		//while (e.hasMoreElements())
		{
			//String key = (String) e.nextElement();
			String value = sourceProp.getProperty(key);
			if (targetProp.containsKey(key))
			{
				value = targetProp.getProperty(key);
			}
			
			System.out.println(String.format("%s=%s", key, value));
			
		}
		
	}

	private void loadProperites(String sourcePropFileName, String targetPropFileName) throws IOException
	{
		InputStream sis = this.getClass().getClassLoader().getResourceAsStream(sourcePropFileName);
		sourceProp = new LinkedProperties();
		sourceProp.load(sis);
		
		InputStream tis = this.getClass().getClassLoader().getResourceAsStream(targetPropFileName);
		targetProp = new LinkedProperties();
		targetProp.load(tis);
	}
}
