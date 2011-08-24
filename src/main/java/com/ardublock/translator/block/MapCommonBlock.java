package com.ardublock.translator.block;

import com.ardublock.translator.Translator;

public class MapCommonBlock extends TranslatorBlock
{
	protected MapCommonBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	public String toCode()
	{
		String ret = "map( ";
		TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);
		ret = ret + tb.toCode();
		ret = ret + " , 0, 1024, 0, 255)";
		return codePrefix + ret + codeSuffix;
	}
	

}
