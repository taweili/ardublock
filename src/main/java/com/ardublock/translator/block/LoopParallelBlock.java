package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;

public class LoopParallelBlock extends TranslatorBlock
{
	public LoopParallelBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	public String toCode() throws SocketNullException
	{
		translator.addTaskLoop(codePrefix);
		TranslatorBlock translatorBlock = getRequiredTranslatorBlockAtSocket(0);
		translator.addTaskPriorityLoop(codePrefix, translatorBlock.toCode());
		
		String ret = "taskLoop(" + codePrefix + ") {\n";
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
