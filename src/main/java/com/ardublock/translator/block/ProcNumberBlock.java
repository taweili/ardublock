package com.ardublock.translator.block;

import com.ardublock.translator.Translator;

public class ProcNumberBlock extends TranslatorBlock
{
	protected ProcNumberBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	public String toCode()
	{
		return codePrefix + "int " + label + codeSuffix;
	}

}
