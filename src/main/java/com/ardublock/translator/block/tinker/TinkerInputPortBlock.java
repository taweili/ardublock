package com.ardublock.translator.block.tinker;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;

public class TinkerInputPortBlock extends TranslatorBlock
{

	public TinkerInputPortBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
		translator.addHeaderFile("TinkerKit.h");
	}

	@Override
	public String toCode() throws SocketNullException
	{
		return codePrefix + label + codeSuffix;
	}
}