package com.ardublock.translator.block;

import com.ardublock.translator.Translator;

public class DigitalLowBlock extends TranslatorBlock
{

	protected DigitalLowBlock(Long blockId, Translator translator) 
	{
		super(blockId, translator);
	}

	public String toCode() 
	{
		return "LOW";
	}

}