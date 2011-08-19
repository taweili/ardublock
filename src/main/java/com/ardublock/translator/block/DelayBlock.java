package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;

public class DelayBlock extends TranslatorBlock
{

	protected DelayBlock(Long blockId, Translator translator)
	{
		super(blockId, translator);
	}

	public String toCode()
	{
		String ret = "delay( ";
		TranslatorBlock translatorBlock = getTranslatorBlockAtSocket(0);
		if (translatorBlock == null)
		{
			throw new SocketNullException();
		}
		ret = ret + translatorBlock.toCode();
		ret = ret + " );\n";
		return ret;
	}

}
