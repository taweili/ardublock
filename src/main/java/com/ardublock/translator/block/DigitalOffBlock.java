package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.basic.ConstBlock;

public class DigitalOffBlock extends ConstBlock
{
	protected DigitalOffBlock(Long blockId, Translator translator)
	{
		super(blockId, translator);
		this.setCode("LOW");
	}

}
