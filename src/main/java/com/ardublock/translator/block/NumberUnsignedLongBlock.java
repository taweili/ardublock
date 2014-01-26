package com.ardublock.translator.block;

import com.ardublock.translator.Translator;

public class NumberUnsignedLongBlock extends TranslatorBlock
{
	public NumberUnsignedLongBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode()
	{
		return codePrefix + label + "UL" + codeSuffix;
	}

}
