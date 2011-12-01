package com.ardublock.translator.block.dfrobot;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.PinReadAnalogBlock;

public class DfrobotAnalogInputBlock extends PinReadAnalogBlock
{
	public DfrobotAnalogInputBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

}
