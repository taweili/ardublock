package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;

import edu.mit.blocks.codeblocks.Block;

public class ProcCallerBlock extends TranslatorBlock
{
	
	public ProcCallerBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	public String toCode() throws SocketNullException
	{
		String ret = codePrefix + label;
		ret = "\t" + ret + "(";
		Block block = translator.getBlock(blockId);
		int numSocket = block.getNumSockets();
		for (int i = 0; i < numSocket; i++) {
			TranslatorBlock translatorBlock = getTranslatorBlockAtSocket(i);
			if (i > 0) {ret = ret + ", ";};
			ret = ret + translatorBlock.toCode();
			//translatorBlock = translatorBlock.nextTranslatorBlock();			
		}

		if (block.getPlug() == null)
		{
			ret = ret + ");\n" + codeSuffix;
		}
		else
		{
			ret = ret + ")" + codeSuffix;
		}
		return ret;
	}
}
