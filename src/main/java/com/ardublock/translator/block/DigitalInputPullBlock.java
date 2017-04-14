package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class DigitalInputPullBlock extends TranslatorBlock
{
	public static final String ARDUBLOCK_DIGITAL_READ_PULLUP_DEFINE =
			/* The charge left on the pin if it was previously OUTPUT does not affect
			 *  the likely result of digitalRead with PULLUP resistors.
			 * If this changes then implement https://github.com/arduino/Arduino/issues/4606
			 *  and delay 5ms if the pinMode has changed */
			"boolean __ardublockDigitalReadPullup(int pinNumber)\n" + 
			"{\n" +
			"pinMode(pinNumber, INPUT_PULLUP);\n" + 
			"return digitalRead(pinNumber);\n" + 
			"}\n\n";
	
	public DigitalInputPullBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);

		translator.addDefinitionCommand(ARDUBLOCK_DIGITAL_READ_PULLUP_DEFINE);
		String ret = "__ardublockDigitalReadPullup(";
		
		ret = ret + translatorBlock.toCode();
		ret = ret + ")";
		return codePrefix + ret + codeSuffix;
	} 

}
