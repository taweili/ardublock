package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;

public class ToneTimeBlock extends TranslatorBlock
{
	protected ToneTimeBlock(Long blockId, Translator translator, String codePrefix,	String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException {
		TranslatorBlock pinBlock = this.getRequiredTranslatorBlockAtSocket(0);
		TranslatorBlock freqBlock = this.getRequiredTranslatorBlockAtSocket(1);
		TranslatorBlock timeBlock = this.getRequiredTranslatorBlockAtSocket(2);
		String ret = "\ttone(" + pinBlock.toCode() + ", " + freqBlock.toCode() + ", " + timeBlock.toCode() + ");\n";
		return ret;
	}
}
