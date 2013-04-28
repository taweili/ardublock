package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;

public class AnalogInputBlock extends TranslatorBlock
{
	public AnalogInputBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	public String toCode() throws SocketNullException
	{
		String ret = "analogRead(A";
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		if (translatorBlock instanceof NumberBlock)
		{
			ret = ret + translatorBlock.toCode();
			ret = ret + ")";
			return codePrefix + ret + codeSuffix;
		}
		else
		{
			throw new BlockException(blockId, "analog pin# must be a number");
		}
	}

}
