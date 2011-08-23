package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.basic.ConstBlock;

public class DigitalHighBlock extends ConstBlock
{

	protected DigitalHighBlock(Long blockId, Translator translator) 
	{
		super(blockId, translator);
		this.setCode("HIGH");
	}
}
