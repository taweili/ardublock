package com.ardublock.translator.block.tinker;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.PinWriteDigitalBlock;

public class TinkerLEDBlock extends PinWriteDigitalBlock
{
	public TinkerLEDBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

}
