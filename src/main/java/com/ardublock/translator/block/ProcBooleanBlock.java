package com.ardublock.translator.block;

import com.ardublock.translator.Translator;

public class ProcBooleanBlock extends TranslatorBlock
{
	protected ProcBooleanBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	public String toCode()
	{
		return codePrefix + "boolean " + label + codeSuffix;
	}

}
