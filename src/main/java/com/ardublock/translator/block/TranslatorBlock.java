package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.adaptor.BlockAdaptor;

abstract public class TranslatorBlock
{
	abstract public String toCode();
	
	protected Long blockId;
	
	private BlockAdaptor blockAdaptor;
	
	private Translator translator;
	
	protected String comment;
	
	protected TranslatorBlock(Long blockId, Translator translator)
	{
		this.blockId = blockId;
		this.translator = translator;
		blockAdaptor = translator.getBlockAdaptor();
	}
	
	protected Translator getTranslator()
	{
		return translator;
	}
	
	protected TranslatorBlock nextTranslatorBlock()
	{
		return blockAdaptor.nextTranslatorBlock(this.translator, blockId);
	}
	
	protected TranslatorBlock getTranslatorBlockAtSocket(int i)
	{
		return blockAdaptor.getTranslatorBlockAtSocket(this.translator, blockId, i);
	}
	
	protected void addHeaderFile(String headerFile)
	{
		Translator translator = getTranslator();
		translator.addHeaderFile(headerFile);
	}
	
	protected void addSetupCommand(String command)
	{
		Translator translator = getTranslator();
		translator.addSetupCommand(command);
	}
	
	protected void addDefinitionCommand(String command)
	{
		Translator translator = getTranslator();
		translator.addDefinitionCommand(command);
	}
	
	protected void setComment(String comment)
	{
		this.comment = comment;
	}
	
	protected String getComment()
	{
		return this.comment;
	}
}
