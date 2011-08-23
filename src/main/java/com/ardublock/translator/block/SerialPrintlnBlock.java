package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;

public class SerialPrintlnBlock extends TranslatorBlock
{
	protected SerialPrintlnBlock(Long blockId, Translator translator)
	{
		super(blockId, translator);
	}

	public String toCode()
	{
		translator.addSetupCommand("Serial.setup(9600);\n");
		TranslatorBlock translatorBlock = getTranslatorBlockAtSocket(0, "Serial.print( ", " );\n");
		if (translatorBlock == null)
		{
			throw new SocketNullException();
		}
		
		String ret = translatorBlock.toCode();
		ret = ret + "Serial.pritln(\"\");\n";
		
		return ret;
	}
}
