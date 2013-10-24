package com.ardublock.translator.block;

import com.ardublock.translator.Translator;

public class PolyBlock extends TranslatorBlock
{
	public PolyBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	public String toCode()
	{
	    String ret="\"";
	    ret+=label;
	    ret+="\"";
		return codePrefix + "\""+label+ "\"" + codeSuffix;
	}

}
