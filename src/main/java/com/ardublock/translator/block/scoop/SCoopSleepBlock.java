package com.ardublock.translator.block.scoop;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class SCoopSleepBlock extends TranslatorBlock
{

	public SCoopSleepBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		String value = translatorBlock.toCode();
		
		String rootBlockName = translator.getRootBlockName().toLowerCase().trim();
		if (rootBlockName.startsWith("scoop"))
		{
			return "sleep(" + value + ");\n";
		}
		else
		{
			return "mySCoop.sleep(" + value + ");\n";
		}
	}

}
