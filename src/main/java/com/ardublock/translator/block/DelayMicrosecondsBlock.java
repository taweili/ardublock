package com.ardublock.translator.block;

import com.ardublock.translator.Translator;

public class DelayMicrosecondsBlock extends TranslatorBlock
{
	protected DelayMicrosecondsBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	public String toCode()
	{
		TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);
		String ret = "delayMicroseconds( " + tb.toCode() + " );\n";
		return ret;
	}

}
