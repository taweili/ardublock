package com.ardublock.translator.block;

import com.ardublock.translator.Translator;

public class FalseBlock extends TranslatorBlock
{
	protected FalseBlock(Long blockId, Translator translator)
	{
		super(blockId, translator);
	}

	public String toCode()
	{
		return "false";
	}

}
