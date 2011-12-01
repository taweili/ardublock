package com.ardublock.translator.block.dfrobot;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.PinReadDigitalBlock;

public class DfrobotDigitalInoutBlock extends PinReadDigitalBlock
{
	public DfrobotDigitalInoutBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

}
