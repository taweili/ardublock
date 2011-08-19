package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;

public class AdditionBlock extends TranslatorBlock
{
	public AdditionBlock(Long blockId, Translator translator)
	{
		super(blockId, translator);
	}

	public String toCode()
	{
		String ret = "( ";
		TranslatorBlock translatorBlock = getTranslatorBlockAtSocket(0);
		if (translatorBlock == null)
		{
			throw new SocketNullException();
		}
		ret = ret + translatorBlock.toCode();
		ret = ret + " + ";
		translatorBlock = getTranslatorBlockAtSocket(1);
		if (translatorBlock == null)
		{
			throw new SocketNullException();
		}
		ret = ret + translatorBlock.toCode();
		ret = ret + " )";
		return ret;
	}

}
