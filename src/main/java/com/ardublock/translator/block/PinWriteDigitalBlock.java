package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
//import com.ardublock.translator.block.basic.InputPortBlock;
import com.ardublock.translator.block.exception.SocketNullException;

public class PinWriteDigitalBlock extends TranslatorBlock
{
	public static final String ARDUBLOCK_DIGITAL_WRITE_DEFINE = "void __ardublockDigitalWrite(int pinNumber, boolean status)\n{\npinMode(pinNumber, OUTPUT);\ndigitalWrite(pinNumber, status);\n}\n";
	
	public PinWriteDigitalBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	public String toCode() throws SocketNullException
	{
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		if (translatorBlock instanceof NumberBlock)
		{
			String portNum = translatorBlock.toCode();
			Long output = Long.parseLong(portNum);
			translator.addOutputPin(output);
			
			String ret = "\tdigitalWrite(";
			ret = ret + portNum;
			ret = ret + ", ";
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
			ret = ret + translatorBlock.toCode();
			ret = ret + ");\n";
			return ret;
		}
		else
		{
			translator.addDefinitionCommand(ARDUBLOCK_DIGITAL_WRITE_DEFINE);
			String ret = "__ardublockDigitalWrite(";
			
			ret = ret + translatorBlock.toCode();
			ret = ret + ", ";
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
			ret = ret + translatorBlock.toCode();
			ret = ret + ");\n";
			return ret;
		}
		
	}

}
