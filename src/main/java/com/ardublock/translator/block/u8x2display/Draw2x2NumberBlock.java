package com.ardublock.translator.block.u8x2display;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;

public class Draw2x2NumberBlock extends DrawNumberBlock
{
	public Draw2x2NumberBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	protected String getCommand()
	{
		return "draw2x2String";
	}
}
