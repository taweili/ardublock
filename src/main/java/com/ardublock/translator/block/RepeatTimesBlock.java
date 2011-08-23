package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;

public class RepeatTimesBlock extends TranslatorBlock
{

	protected RepeatTimesBlock(Long blockId, Translator translator)
	{
		super(blockId, translator);
	}

	public String toCode()
	{
		String varName = translator.buildVariableName();
		translator.addDefinitionCommand("int " + varName + ";");
		String ret = "for (" + varName + "=0; " + varName + "< ( ";
		TranslatorBlock translatorBlock = getTranslatorBlockAtSocket(0);
		if (translatorBlock == null)
		{
			throw new SocketNullException();
		}
		ret = ret + translatorBlock.toCode();
		ret = ret + " ); ++" + varName + " )\n{\n";
		
		
		translatorBlock = getTranslatorBlockAtSocket(1);
		while (translatorBlock != null)
		{
			ret = ret + translatorBlock.toCode();
			translatorBlock = translatorBlock.nextTranslatorBlock();
		}
		
		ret = ret + "}\n\n";
		return ret;
	}

}
