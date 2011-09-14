package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;

public class DelayBlock extends TranslatorBlock
{

	protected DelayBlock(Long blockId, Translator translator)
	{
		super(blockId, translator);
	}

	public String toCode() throws SocketNullException
	{
		String ret = "delay( ";
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		ret = ret + translatorBlock.toCode();
		ret = ret + " );\n";
		return ret;
	}

}
