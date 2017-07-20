package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class StringEmptyBlock extends TranslatorBlock
{
	public StringEmptyBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		StringBuilder ret = new StringBuilder();
		ret.append("");
		TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);
		ret.append(tb.toCode());
		ret.append(".length() == 0");
		return codePrefix + ret + codeSuffix;
	}
}
