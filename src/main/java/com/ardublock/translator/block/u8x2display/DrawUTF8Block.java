package com.ardublock.translator.block.u8x2display;

import com.ardublock.translator.Translator;

public class DrawUTF8Block extends DrawStringBlock
{
	public DrawUTF8Block(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	protected String getCommand()
	{
		return "drawUTF8";
	}
}
