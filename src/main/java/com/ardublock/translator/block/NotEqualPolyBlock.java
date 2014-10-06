package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class NotEqualPolyBlock extends TranslatorBlock
{
	public NotEqualPolyBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String ret = "( ( ";
		TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);
		ret = ret + tb.toCode().replace("\"","\'");
		ret = ret + " ) != (";
		tb = this.getRequiredTranslatorBlockAtSocket(1);
		ret = ret + tb.toCode().replace("\"","\'");
		ret = ret + ") )";
		return codePrefix + ret + codeSuffix;
	}
}
