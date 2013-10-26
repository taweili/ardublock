package com.ardublock.util.codegen;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public class CodeGen
{
	public static void main(String args[]) throws InvalidFormatException, IOException
	{
		CodeGen a = new CodeGen();
		a.work();
	}
	
	private String digitalInputBlockGenusTemplate;
	private String digitalOutputBlockGenusTemplate;
	private String blockDrawerTemplate;
	
	public void work() throws InvalidFormatException, IOException
	{
		setupTemplateString();
		
		XlsReader xlsReader = new XlsReader();
		xlsReader.setFilePath("/home/heqichen/my/project/taweili/ardublock/ardublock/src/test/resources/seeedstudio/digitalout.xls");
		
		OutputSet outputSet = new OutputSet();
		
		generateArdublockCode(xlsReader, outputSet);
		
		output(outputSet);
	}
	

	
	private static String DIGITAL_INPUT_BLOCK_GENUS_TEMPLATE_PATH = "codegen/digitalinput.xml";
	private static String DIGITAL_OUTPUT_BLOCK_GENUS_TEMPLATE_PATH = "codegen/digitaloutput.xml";
	private static String BLOCK_DRAWER_TEMPLATE_PATH = "codegen/block_drawer.xml";
	
	private void setupTemplateString() throws IOException
	{
		digitalInputBlockGenusTemplate = loadStringFromClasspath(DIGITAL_INPUT_BLOCK_GENUS_TEMPLATE_PATH);
		digitalOutputBlockGenusTemplate = loadStringFromClasspath(DIGITAL_OUTPUT_BLOCK_GENUS_TEMPLATE_PATH);
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
		outputSet.addBlockName("bg." + block.getBlockGenusName() + "=" + block.getBlockShowName());
		outputSet.addBlockDescription("bg." + block.getBlockGenusName() + ".description=" + block.getBlockDescription());
		outputSet.addBlockType(block.getBlockGenusName() + "=" + block.getBlockType());
	}
	
	private String makeBlockMemberFromBlockDescription(BlockDescription block)
	{
		String ret = blockDrawerTemplate;
		ret = ret.replaceAll("#block_name#", block.getBlockGenusName());
		return ret;
	}


	private String makeBlockGenusFromBlockDescription(BlockDescription block)
	{
		if (block.getBlockType().equals("digitalInput") || block.getBlockType().equals("inversedDigitalInput"))
			return makeDigitalInputBlockGenusFromTemplate(digitalInputBlockGenusTemplate, block);
		
		if (block.getBlockType().equals("digitalOutput") || block.getBlockType().equals("inversedDigitalOutput"))
			return makeDigitalInputBlockGenusFromTemplate(digitalOutputBlockGenusTemplate, block);
		
		return null;
	}
	
	private String makeDigitalInputBlockGenusFromTemplate(String blockGenusTemplate, BlockDescription block)
	{
		String ret = blockGenusTemplate;
		ret = ret.replaceAll("#block_genus_name#", block.getBlockGenusName());
		ret = ret.replaceAll("#block_image_path#", block.getBlockImagePath());
		ret = ret.replaceAll("#block_color#", block.getBlockColor());
		return ret;
	}


	private void output(OutputSet outputSet)
	{
		outputSection(outputSet.getBlockGenusList());
		outputSection(outputSet.getBlockDrawerList());
		outputSection(outputSet.getBlockNameList());
		outputSection(outputSet.getBlockDescriptionList());
		outputSection(outputSet.getBlockTypeList());
	}
	
	private void outputSection(List<String> section)
	{
		for (String entry : section)
		{
			System.out.println(entry);
		}
		System.out.println();
	}
}
