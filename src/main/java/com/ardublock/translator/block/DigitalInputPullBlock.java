package com.ardublock.translator.block;

import com.ardublock.translator.Translator;

public class DigitalInputPullBlock extends DigitalInputBlock
{
	public static final String ARDUBLOCK_DIGITAL_READ_INPUT_PULLUP_DEFINE = 
"boolean __ardublockDigitalRead(int pinNumber)\n" +
"{\n" +
"pinMode(pinNumber, INPUT);\n" +
"digitalWrite(pinNumber, HIGH);\n" +
"return digitalRead(pinNumber);\n" +
"}\n" +
"\n";
	
	public DigitalInputPullBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}


	
}
