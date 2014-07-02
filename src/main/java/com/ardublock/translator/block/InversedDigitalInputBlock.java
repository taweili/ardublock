package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class InversedDigitalInputBlock extends DigitalInputBlock
{

	public InversedDigitalInputBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	protected String generateCodeUsingNumberBlock(TranslatorBlock translatorBlock) throws SocketNullException, SubroutineNotDeclaredException
	{
		String number;
		number = translatorBlock.toCode();
		translator.addInputPin(number.trim());
		
		String ret = "!digitalRead(";
		ret = ret + number;
		ret = ret + ")";
		return codePrefix + ret + codeSuffix;
	}
	
	protected String generateCodeUsingNonNumberBlock(TranslatorBlock translatorBlock) throws SocketNullException, SubroutineNotDeclaredException
	{
		translator.addDefinitionCommand(ARDUBLOCK_DIGITAL_READ_DEFINE);
		String ret = "!__ardublockDigitalRead(";
		
		ret = ret + translatorBlock.toCode();
		ret = ret + ")";
		return codePrefix + ret + codeSuffix;
	}
}
