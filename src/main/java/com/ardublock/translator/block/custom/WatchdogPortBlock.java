package com.ardublock.translator.block.custom;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;

public class WatchdogPortBlock extends TranslatorBlock
{

	public WatchdogPortBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
		
	}

	@Override
	public String toCode() throws SocketNullException
	{
		return codePrefix + label + codeSuffix;
	}
}