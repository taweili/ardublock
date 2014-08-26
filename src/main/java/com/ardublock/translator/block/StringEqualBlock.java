package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class StringEqualBlock extends TranslatorBlock
{
	public StringEqualBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		StringBuilder ret = new StringBuilder();
		ret.append("strcmp(");
		TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);
		ret.append(tb.toCode());
		ret.append(", ");
		tb = this.getRequiredTranslatorBlockAtSocket(1);
		ret.append(tb.toCode());
		ret.append(") == 0");
		return codePrefix + ret + codeSuffix;
	}
}
