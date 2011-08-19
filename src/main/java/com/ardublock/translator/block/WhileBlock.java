package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;

public class WhileBlock extends TranslatorBlock
{

	protected WhileBlock(Long blockId, Translator translator)
	{
		super(blockId, translator);
	}
	
	public String toCode()
	{
		String ret = "while ( ";
		TranslatorBlock translatorBlock = getTranslatorBlockAtSocket(0);
		if (translatorBlock == null)
		{
			throw new SocketNullException();
		}
		ret = ret + translatorBlock.toCode();
		ret = ret + " )\n{\n";
		translatorBlock = getTranslatorBlockAtSocket(1);
		while (translatorBlock != null)
		{
			ret = ret + translatorBlock.toCode();
			translatorBlock = translatorBlock.nextTranslatorBlock();
		}
		ret = ret + "}\n\n";
		return ret;
	}

}
