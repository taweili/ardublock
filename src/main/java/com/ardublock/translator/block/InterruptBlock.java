package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;

public class InterruptBlock extends TranslatorBlock
{
	protected InterruptBlock(Long blockId, Translator translator, String codePrefix,	String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException {
		String ret = "\tinterrupts();\n";
		return ret;
	}
}
