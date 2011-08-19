package com.ardublock.translator.block;

import com.ardublock.translator.Translator;

public class NumberBlock extends TranslatorBlock
{
	private String label;
	protected NumberBlock(Long blockId, String label, Translator translator)
	{
		super(blockId, translator);
		this.label = label;
	}

	public String toCode()
	{
		return label;
	}

}
