package com.ardublock.translator.block;

import com.ardublock.translator.Translator;

public class NumberDoubleBlock extends TranslatorBlock
{
	public NumberDoubleBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode()
	{
		if ( ! label.contains(".")) {
			label = label + ".0";		// double constants are indicated by decimal points
		}
		return codePrefix + label + codeSuffix;
	}

}
