package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.adaptor.BlockAdaptor;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

abstract public class TranslatorBlock
{
	abstract public String toCode() throws SocketNullException, SubroutineNotDeclaredException, BlockException;
	
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
	
	public TranslatorBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
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
	
	public TranslatorBlock nextTranslatorBlock()
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
	
	protected TranslatorBlock getRequiredTranslatorBlockAtSocket(int i) throws SocketNullException
	{
		return this.getRequiredTranslatorBlockAtSocket(i, "", "");
	}
	
	protected TranslatorBlock getRequiredTranslatorBlockAtSocket(int i, String codePrefix, String codeSuffix) throws SocketNullException
	{
		TranslatorBlock translatorBlock = blockAdaptor.getTranslatorBlockAtSocket(this.translator, blockId, i, codePrefix, codeSuffix);
		if (translatorBlock == null)
		{
			throw new SocketNullException(blockId);
		}
		return translatorBlock;
	}
	
	protected void setComment(String comment)
	{
		this.comment = comment;
	}
	
	protected String getComment()
	{
		return this.comment;
	}
	
	public void onTranslateBodyFinished() throws SocketNullException, SubroutineNotDeclaredException{}
	
}
