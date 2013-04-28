package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.basic.ConstBlock;

public class TrueBlock extends ConstBlock
{
	public TrueBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
		this.setCode("true");
	}
}
