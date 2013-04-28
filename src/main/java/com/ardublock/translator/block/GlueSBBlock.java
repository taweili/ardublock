package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;

public class GlueSBBlock extends TranslatorBlock
{
	public GlueSBBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	public String toCode() throws SocketNullException
	{
		String ret = "";
		TranslatorBlock translatorBlock = this.getTranslatorBlockAtSocket(0, codePrefix, codeSuffix);
		if (translatorBlock != null)
		{
			ret = translatorBlock.toCode();
		}
		return ret;
	}

}
