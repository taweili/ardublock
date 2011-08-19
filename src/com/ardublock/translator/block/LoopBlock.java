package com.ardublock.translator.block;

import com.ardublock.translator.Translator;

public class LoopBlock extends TranslatorBlock
{
	public LoopBlock(Long blockId, Translator translator)
	{
		super(blockId, translator);
	}

	public String toCode()
	{
		String ret;
		ret = "void loop()\n{\n";
		TranslatorBlock translatorBlock = getTranslatorBlockAtSocket(0);
		while (translatorBlock != null)
		{
			ret = ret + translatorBlock.toCode();
			translatorBlock = translatorBlock.nextTranslatorBlock();
		}
		ret = ret + "}\n\n";
		return ret;
	}
}
