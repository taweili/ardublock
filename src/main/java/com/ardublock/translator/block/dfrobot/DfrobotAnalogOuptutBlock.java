package com.ardublock.translator.block.dfrobot;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.PinWriteAnalogBlock;

public class DfrobotAnalogOuptutBlock extends PinWriteAnalogBlock
{
	public DfrobotAnalogOuptutBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

}
