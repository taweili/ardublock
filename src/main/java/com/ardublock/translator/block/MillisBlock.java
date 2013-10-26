package com.ardublock.translator.block;

import com.ardublock.translator.Translator;

public class MillisBlock extends TranslatorBlock
{
	public MillisBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode()
	{
		String ret = "millis()";
		return codePrefix + ret + codeSuffix;
	}
	
}
