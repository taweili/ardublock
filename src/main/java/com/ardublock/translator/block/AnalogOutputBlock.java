package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;

public class AnalogOutputBlock extends TranslatorBlock
{
	public AnalogOutputBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	public String toCode() throws SocketNullException
	{
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		String portNum = translatorBlock.toCode();
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
		String value = translatorBlock.toCode();
		
		String setupCode = "pinMode( " + portNum + " , OUTPUT);";
		translator.addSetupCommand(setupCode);
		
		String ret = "analogWrite(" + portNum + " , " + value + ");\n";
		return ret;
	}

}


