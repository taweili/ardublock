package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class DigitalInputBlock extends TranslatorBlock
{
	public static final String ARDUBLOCK_DIGITAL_READ_DEFINE =
			/* The charge left on the pin if it was previously OUTPUT does affect
			 *  the likely result of digitalRead **if the pin is floating**.
			 * I can find nothing to justify a need for a 'settling period' delay
			 *  if the pin mode has been changed.
			 *  and delay 5ms if the pinMode has changed */
			"boolean __ardublockDigitalRead(int pinNumber)\n" + 
			"{\n" +
			"pinMode(pinNumber, INPUT);\n" + 
			"return digitalRead(pinNumber);\n" + 
			"}\n\n";
	
	public DigitalInputBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);

		translator.addDefinitionCommand(ARDUBLOCK_DIGITAL_READ_DEFINE);
		String ret = "__ardublockDigitalRead(";
		
		ret = ret + translatorBlock.toCode();
		ret = ret + ")";
		return codePrefix + ret + codeSuffix;
	} 

}
