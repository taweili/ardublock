package com.ardublock.translator.block.dfrobot;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;

public class DfrobotPushButtonBlock extends DfrobotDigitalInoutBlock
{
	public DfrobotPushButtonBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException
	{
		String code = super.toCode();
		code = "!(" + code + ")";
		return code;
	}
}
