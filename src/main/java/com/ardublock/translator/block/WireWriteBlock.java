package com.ardublock.translator.block;

import com.ardublock.translator.Translator;

public class WireWriteBlock extends TranslatorBlock
{
	protected WireWriteBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	public String toCode()
	{
		WireReadBlock.setupWireEnvironment(translator);
		String ret = "__ardublockI2cWriteData( ";
		TranslatorBlock tb = getRequiredTranslatorBlockAtSocket(0);
		ret = ret + tb.toCode();
		ret = ret + " , ";
		tb = getRequiredTranslatorBlockAtSocket(1);
		ret = ret + tb.toCode();
		ret = ret + " , ";
		tb = getRequiredTranslatorBlockAtSocket(2);
		ret = ret + tb.toCode();
		ret = ret + " );\n";
		return ret;
	}

}
