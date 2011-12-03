package com.ardublock.translator.block.dfrobot;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;

public class DfrobotLedPwmBlock extends TranslatorBlock
{
	public DfrobotLedPwmBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	public String toCode() throws SocketNullException
	{
		String ret = "analogWrite(";
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		ret = ret + translatorBlock.toCode();
		ret = ret + ", 255 - (";
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
		ret = ret + translatorBlock.toCode();
		ret = ret + " ));\n";
		return ret;
	}
	
}
