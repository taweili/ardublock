package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;

public class PinWriteAnalogBlock extends TranslatorBlock
{
	public PinWriteAnalogBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	public String toCode() throws SocketNullException
	{
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		String portNum = translatorBlock.toCode();
		Long output = Long.parseLong(portNum);
		//String setupCode = "\tpinMode(" + number + ", INPUT);";
		//translator.addSetupCommand(setupCode);
		translator.addOutputPin(output);

		translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
		String value = translatorBlock.toCode();
		
		//String setupCode = "\tpinMode(" + portNum + ", OUTPUT);";
		//translator.addSetupCommand(setupCode);
		
		String ret = "\tanalogWrite(" + portNum + ", " + value + ");\n";
		return ret;
	}

}


