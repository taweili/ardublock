package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;

public class LoopPriorityBlock extends TranslatorBlock
{
	public LoopPriorityBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	public String toCode() throws SocketNullException
	{
		String ret = "void loop() {\n";
		TranslatorBlock translatorBlock = getRequiredTranslatorBlockAtSocket(0);
		translator.addSetupCommand("initMainLoopPriority( " + translatorBlock.toCode() + " );");
		
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
