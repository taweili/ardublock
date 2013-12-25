package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;

public class NoToneBlock extends TranslatorBlock
{
	protected NoToneBlock(Long blockId, Translator translator, String codePrefix,	String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException
	{
		TranslatorBlock pinBlock = this.getRequiredTranslatorBlockAtSocket(0);
		String ret = "\tnoTone(" + pinBlock.toCode() + ");\n";
		return ret;
	}
}
