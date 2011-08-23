package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.basic.ConstBlock;

public class FalseBlock extends ConstBlock
{
	protected FalseBlock(Long blockId, Translator translator)
	{
		super(blockId, translator);
		this.setCode("false");
	}
}
