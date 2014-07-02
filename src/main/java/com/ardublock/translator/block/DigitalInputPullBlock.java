package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class DigitalInputPullBlock extends DigitalInputBlock
{
	public static final String ARDUBLOCK_DIGITAL_READ_INPUT_PULLUP_DEFINE = 
"void __ardublockDigitalInputPullup(int pinNumber)\n" +
"{\n" +
"pinMode(pinNumber, INPUT);\n" +
"digitalWrite(pinNumber, HIGH);\n" +
"}\n" +
"\n";
	
	public DigitalInputPullBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	@Override
	protected String generateCodeUsingNumberBlock(TranslatorBlock translatorBlock) throws SocketNullException, SubroutineNotDeclaredException
	{
		String number;
		number = translatorBlock.toCode();
		translator.addInputPin(number.trim());
		translator.addSetupCommand("digitalWrite(" + number + ", HIGH);");
		return "" ;
	}
	
	@Override
	protected String generateCodeUsingNonNumberBlock(TranslatorBlock translatorBlock) throws SocketNullException, SubroutineNotDeclaredException
	{
		translator.addDefinitionCommand(ARDUBLOCK_DIGITAL_READ_INPUT_PULLUP_DEFINE);
		translator.addSetupCommand("__ardublockDigitalInputPullup(" + translatorBlock.toCode() + ");");
		return "";
	}

	
}
