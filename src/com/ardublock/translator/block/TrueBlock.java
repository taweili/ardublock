package com.ardublock.translator.block;

import com.ardublock.translator.Translator;

public class TrueBlock extends TranslatorBlock
{
	protected TrueBlock(Long blockId, Translator translator)
	{
		super(blockId, translator);
	}

	public String toCode()
	{
		return "true";
	}

}
