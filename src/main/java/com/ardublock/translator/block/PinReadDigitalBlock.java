package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;

public class PinReadDigitalBlock extends TranslatorBlock
{
	public static final String ARDUBLOCK_DIGITAL_READ_DEFINE = "boolean __ardublockDigitalRead(int pinNumber)\n{\npinMode(pinNumber, INPUT);\nreturn digitalRead(pinNumber);\n}\n\n";
	
	public PinReadDigitalBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	public String toCode() throws SocketNullException
	{
		translator.addDefinitionCommand(ARDUBLOCK_DIGITAL_READ_DEFINE);
		String ret = "__ardublockDigitalRead(";
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		ret = ret + translatorBlock.toCode();
		ret = ret + ")";
		return codePrefix + ret + codeSuffix;
	}

}
