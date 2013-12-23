package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;

public class ContinueBlock extends TranslatorBlock
{

	protected ContinueBlock(Long blockId, Translator translator)
	{
		super(blockId, translator);
	}
	
	public String toCode() throws SocketNullException
	{
		String ret = "\tContinue;\n";
		return ret;
	}

}
