package com.ardublock.translator.block.basic;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;

public abstract class ConstBlock extends TranslatorBlock
{
	private String code;
	protected ConstBlock(Long blockId, Translator translator)
	{
		super(blockId, translator);
		this.code = "undefined";
	}

	protected void setCode(String code)
	{
		this.code = code;
	}
	
	public String toCode()
	{
		return code;
	}

}
