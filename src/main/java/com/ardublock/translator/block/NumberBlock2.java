package com.ardublock.translator.block;

import com.ardublock.translator.Translator;

public class NumberBlock2 extends TranslatorBlock
{
	protected NumberBlock2(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	public String toCode()
	{
		return codePrefix + label + codeSuffix;
	}

}
