package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class RandomBlock extends TranslatorBlock
{

	public RandomBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String ret = "random( ";
		TranslatorBlock translatorBlock = getRequiredTranslatorBlockAtSocket(0);
		ret = ret + translatorBlock.toCode();
		ret = ret + " )";
		translator.addSetupCommand("randomSeed(analogRead(A0) + analogRead(A1) + analogRead(A2));");
		return codePrefix + ret + codeSuffix;
	}

}
