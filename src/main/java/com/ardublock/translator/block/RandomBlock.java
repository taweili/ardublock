package com.ardublock.translator.block;

import com.ardublock.translator.Translator;

public class RandomBlock extends TranslatorBlock
{

	protected RandomBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	public String toCode()
	{
		String ret = "random( ";
		TranslatorBlock translatorBlock = getRequiredTranslatorBlockAtSocket(0);
		ret = ret + translatorBlock.toCode();
		ret = ret + " )";
		translator.addSetupCommand("randomrandomSeed(analogRead(A0) + analogRead(A1) + analogRead(A2));");
		return codePrefix + ret + codeSuffix;
	}

}
