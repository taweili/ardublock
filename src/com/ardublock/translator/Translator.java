package com.ardublock.translator;

import java.util.HashSet;
import java.util.Set;

import edu.mit.blocks.codeblocks.Block;
import com.ardublock.translator.adaptor.BlockAdaptor;
import com.ardublock.translator.adaptor.OpenBlocksAdaptor;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.TranslatorBlockFactory;


public class Translator
{
	private Set<String> headerFileSet;
	private Set<String> definitionSet;
	private Set<String> setupSet;
	private BlockAdaptor blockAdaptor;
	
	public Translator()
	{
		reset();
	}
	
	public String translate(Long blockId)
	{
		reset();
		
		TranslatorBlockFactory translatorBlockFactory = new TranslatorBlockFactory();
		Block block = Block.getBlock(blockId);
		
		TranslatorBlock rootTranslatorBlock = translatorBlockFactory.buildTranslatorBlock(this, blockId, block.getGenusName(), block.getBlockLabel());
		
		String loopCommand = rootTranslatorBlock.toCode();
		String headerCommand = "";
		if (!headerFileSet.isEmpty())
		{
			for (String file:headerFileSet)
			{
				headerCommand = headerCommand  + "#include <" + file + ">\n";
			}
			headerCommand = headerCommand + "\n";
		}
		
		if (!definitionSet.isEmpty())
		{
			for (String command:definitionSet)
			{
				headerCommand = headerCommand + command + "\n"; 
			}
			headerCommand = headerCommand + "\n";
		}
		
		headerCommand = headerCommand + "void setup()\n{\n";
		if (!setupSet.isEmpty())
		{
			for (String command:setupSet)
			{
				headerCommand = headerCommand + command + "\n";
			}
		}
		headerCommand = headerCommand + "}\n\n";
		
		String program = headerCommand + loopCommand;
		return program;
	}
	
	public BlockAdaptor getBlockAdaptor()
	{
		return blockAdaptor;
	}
	
	private void reset()
	{
		headerFileSet = new HashSet<String>();
		definitionSet = new HashSet<String>();
		setupSet = new HashSet<String>();
		blockAdaptor = buildOpenBlocksAdaptor();
	}
	
	private BlockAdaptor buildOpenBlocksAdaptor()
	{
		return new OpenBlocksAdaptor();
	}
	
	public void addHeaderFile(String headerFile)
	{
		headerFileSet.add(headerFile);
	}
	
	public void addSetupCommand(String command)
	{
		setupSet.add(command);
	}
	
	public void addDefinitionCommand(String command)
	{
		definitionSet.add(command);
	}
}
