package com.ardublock.util.codegen;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public class CodeGen
{
	public static void main(String args[]) throws InvalidFormatException, IOException
	{
		CodeGen a = new CodeGen();
		a.work();
	}
	
	private String blockGenusTemplate;
	
	public void work() throws InvalidFormatException, IOException
	{
		setupTemplateString();
		
		XlsReader xlsReader = new XlsReader();
		xlsReader.setFilePath("/home/heqichen/my/project/taweili/ardublock/ardublock/src/test/resources/seeedstudio/test.xls");
		
		OutputSet outputSet = new OutputSet();
		
		generateArdublockCode(xlsReader, outputSet);
		
		for (String blockGenus : outputSet.getBlockGenusList())
		{
			System.out.println(blockGenus);
			System.out.println();
		}
	}
	

	
	private static String DIGITAL_INPUT_BLOCK_GENUS_TEMPLATE_PATH = "codegen/digitalinput.xml";
	private void setupTemplateString() throws IOException
	{
		blockGenusTemplate = loadStringFromClasspath(DIGITAL_INPUT_BLOCK_GENUS_TEMPLATE_PATH);
	}


	private String loadStringFromClasspath(String pathInClasspath) throws IOException {
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(pathInClasspath);
		String ret = null;
		try
		{
			ret = IOUtils.toString(is);
		}
		finally
		{
			IOUtils.closeQuietly(is);
		}
		return ret;
	}


	private void generateArdublockCode(XlsReader xlsReader, OutputSet outputSet) throws InvalidFormatException, IOException
	{
		xlsReader.reset();
		while (xlsReader.hasNext())
		{
			BlockDescription block = xlsReader.nextBlockDescription();
			putToOutputSet(block, outputSet);
		}
	}

	private void putToOutputSet(BlockDescription block, OutputSet outputSet)
	{
		outputSet.addEntry(makeBlockGenusFromBlockDescription(block));
	}
	
	private String makeBlockGenusFromBlockDescription(BlockDescription block)
	{
		String ret = blockGenusTemplate;
		ret = ret.replaceAll("#block_genus_name#", block.getBlockGenusName());
		ret = ret.replaceAll("#block_image_path#", block.getBlockImagePath());
		return ret;
	}
}
