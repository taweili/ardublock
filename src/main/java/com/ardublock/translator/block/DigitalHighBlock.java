package com.ardublock.translator.block;

import com.ardublock.translator.Translator;

public class DigitalHighBlock extends TranslatorBlock
{

	protected DigitalHighBlock(Long blockId, Translator translator) 
	{
		super(blockId, translator);
	}

	public String toCode() 
	{
		return "HIGH";
	}

}
