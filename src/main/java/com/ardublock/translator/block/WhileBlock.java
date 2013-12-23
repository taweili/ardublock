package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;

public class WhileBlock extends TranslatorBlock
{

	protected WhileBlock(Long blockId, Translator translator)
	{
		super(blockId, translator);
	}
	
	public String toCode() throws SocketNullException
	{
		String ret = "\twhile ( ";
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		ret = ret + translatorBlock.toCode();
		ret = ret + " ) {\n";
		translatorBlock = getTranslatorBlockAtSocket(1);
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
