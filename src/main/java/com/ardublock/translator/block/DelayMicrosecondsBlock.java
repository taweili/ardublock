package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;

public class DelayMicrosecondsBlock extends TranslatorBlock
{
	public DelayMicrosecondsBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	public String toCode() throws SocketNullException
	{
		TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);
		String ret = "delayMicroseconds( " + tb.toCode() + " );\n";
		return ret;
	}

}
