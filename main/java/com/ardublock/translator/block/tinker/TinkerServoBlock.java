package com.ardublock.translator.block.tinker;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.ServoBlock;

public class TinkerServoBlock extends ServoBlock
{

	public TinkerServoBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
		translator.addHeaderFile("TinkerKit.h");
	}

}
