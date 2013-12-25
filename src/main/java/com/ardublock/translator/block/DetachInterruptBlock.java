package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;

public class DetachInterruptBlock extends TranslatorBlock
{
	protected DetachInterruptBlock(Long blockId, Translator translator, String codePrefix,	String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException {
		String ret = "\tdetachInterrupts( ";
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		ret = ret + translatorBlock.toCode();
		ret = ret + " );\n";
		return ret;
	}
}
