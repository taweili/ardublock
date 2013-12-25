package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;

public class IfelseBlock extends TranslatorBlock
{
	protected IfelseBlock(Long blockId, Translator translator)
	{
		super(blockId, translator);
	}

	public String toCode() throws SocketNullException
	{
		String ret = "\tif (";
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		ret = ret + translatorBlock.toCode();
		ret = ret + ") {\n";
		translatorBlock = getTranslatorBlockAtSocket(1);
		while (translatorBlock != null)
		{
			ret = ret + translatorBlock.toCode();
			translatorBlock = translatorBlock.nextTranslatorBlock();
		}
		ret = ret + "}\nelse {\n\t";
		translatorBlock = getTranslatorBlockAtSocket(2);
		while (translatorBlock != null)
		{
			ret = ret + translatorBlock.toCode();
			translatorBlock = translatorBlock.nextTranslatorBlock();
		}
		ret = ret.replace("\n", "\n\t");
		ret = ret + "}\n";
		return ret;
	}

}
