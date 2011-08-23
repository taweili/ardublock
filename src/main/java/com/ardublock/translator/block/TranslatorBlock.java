package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.adaptor.BlockAdaptor;

abstract public class TranslatorBlock
{
	abstract public String toCode();
	
	protected Long blockId;
	
	private BlockAdaptor blockAdaptor;
	
	protected Translator translator;
	
	protected String label;
	protected String comment;
	
	protected String codePrefix;
	protected String codeSuffix;
	
	protected TranslatorBlock(Long blockId, Translator translator)
	{
		this.blockId = blockId;
		this.translator = translator;
		this.blockAdaptor = translator.getBlockAdaptor();
		this.codePrefix = "";
		this.codeSuffix = "";
		this.label = "";
	}
	
	protected TranslatorBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix)
	{
		this.blockId = blockId;
		this.translator = translator;
		this.blockAdaptor = translator.getBlockAdaptor();
		this.codePrefix = codePrefix;
		this.codeSuffix = codeSuffix;
		this.label = "";
	}
	
	protected TranslatorBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		this.blockId = blockId;
		this.translator = translator;
		this.blockAdaptor = translator.getBlockAdaptor();
		this.codePrefix = codePrefix;
		this.codeSuffix = codeSuffix;
		this.label = label;
	}
	
	protected Translator getTranslator()
	{
		return translator;
	}
	
	protected TranslatorBlock nextTranslatorBlock()
	{
		return this.nextTranslatorBlock("", "");
	}
	
	protected TranslatorBlock nextTranslatorBlock(String codePrefix, String codeSuffix)
	{
		return blockAdaptor.nextTranslatorBlock(this.translator, blockId, codePrefix, codeSuffix);
	}
	
	protected TranslatorBlock getTranslatorBlockAtSocket(int i)
	{
		return this.getTranslatorBlockAtSocket(i, "", "");
	}
	
	protected TranslatorBlock getTranslatorBlockAtSocket(int i, String codePrefix, String codeSuffix)
	{
		return blockAdaptor.getTranslatorBlockAtSocket(this.translator, blockId, i, codePrefix, codeSuffix);
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
