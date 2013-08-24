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
	private String blockDrawerTemplate;
	
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
		
		for (String blockMember : outputSet.getBlockDrawerList())
		{
			System.out.println(blockMember);
		}
	}
	

	
	private static String DIGITAL_INPUT_BLOCK_GENUS_TEMPLATE_PATH = "codegen/digitalinput.xml";
	private static String BLOCK_DRAWER_TEMPLATE_PATH = "codegen/block_drawer.xml";
	
	private void setupTemplateString() throws IOException
	{
		blockGenusTemplate = loadStringFromClasspath(DIGITAL_INPUT_BLOCK_GENUS_TEMPLATE_PATH);
		blockDrawerTemplate = loadStringFromClasspath(BLOCK_DRAWER_TEMPLATE_PATH);
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
		outputSet.addBlockGenus(makeBlockGenusFromBlockDescription(block));
		outputSet.addBlockMember(makeBlockMemberFromBlockDescription(block));
	}
	
	private String makeBlockMemberFromBlockDescription(BlockDescription block)
	{
		String ret = blockDrawerTemplate;
		ret = ret.replaceAll("#block_name#", block.getBlockGenusName());
		return ret;
	}


	private String makeBlockGenusFromBlockDescription(BlockDescription block)
	{
		String ret = blockGenusTemplate;
		ret = ret.replaceAll("#block_genus_name#", block.getBlockGenusName());
		ret = ret.replaceAll("#block_image_path#", block.getBlockImagePath());
		ret = ret.replaceAll("#block_color#", block.getBlockColor());
		return ret;
	}
}
