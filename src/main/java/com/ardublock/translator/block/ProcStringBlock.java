package com.ardublock.translator.block;

import com.ardublock.translator.Translator;

public class ProcStringBlock extends TranslatorBlock
{
	protected ProcStringBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	public String toCode()
	{
		return codePrefix + "String " + label + codeSuffix;
	}

}
