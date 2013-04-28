package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;

public class TanBlock extends TranslatorBlock
{
	public TanBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	public String toCode() throws SocketNullException
	{
		String ret = "tan( ";
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		ret = ret + translatorBlock.toCode();
		ret = ret + " )";
		return codePrefix + ret + codeSuffix;
	}
	
}
