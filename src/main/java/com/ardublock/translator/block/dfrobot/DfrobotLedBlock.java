package com.ardublock.translator.block.dfrobot;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.NumberBlock;
import com.ardublock.translator.block.PinWriteDigitalBlock;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;

public class DfrobotLedBlock extends TranslatorBlock
{

	//public static final String ARDUBLOCK_DIGITAL_WRITE_DEFINE = "void __ardublockDigitalWrite(int pinNumber, boolean status)\n{\npinMode(pinNumber, OUTPUT);\ndigitalWrite(pinNumber, status);\n}\n";
	
	public DfrobotLedBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	public String toCode() throws SocketNullException
	{
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		String ret;
		if (translatorBlock instanceof NumberBlock)
		{
			String number = translatorBlock.toCode();
			String setupCode = "pinMode( " + number + " , OUTPUT);";
			translator.addSetupCommand(setupCode);
			ret = "digitalWrite( ";
			
			ret = ret + number;
		}
		else
		{
			translator.addDefinitionCommand(PinWriteDigitalBlock.ARDUBLOCK_DIGITAL_WRITE_DEFINE);
			ret = "__ardublockDigitalWrite(";
			
			ret = ret + translatorBlock.toCode();
		}
		ret = ret + " , !(";
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
		ret = ret + translatorBlock.toCode();
		ret = ret + " ));\n";
		return ret;
		
	}
}
