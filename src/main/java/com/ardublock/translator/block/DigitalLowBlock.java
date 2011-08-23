package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.basic.ConstBlock;

public class DigitalLowBlock extends ConstBlock
{

	protected DigitalLowBlock(Long blockId, Translator translator) 
	{
		super(blockId, translator);
		this.setCode("LOW");
	}
}