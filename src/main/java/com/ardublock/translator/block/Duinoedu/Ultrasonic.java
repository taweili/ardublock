package com.ardublock.translator.block.Duinoedu;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Ultrasonic extends TranslatorBlock {
	public Ultrasonic(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String Pin;
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		Pin = translatorBlock.toCode();

		
		translator.addHeaderFile("Ultrasonic.h");
		translator.addDefinitionCommand("//libraries at http://www.duinoedu.com/\nUltrasonic monUltrasonic_pin"+Pin+"("+Pin +");"	);
		
		String ret = "monUltrasonic_pin"+Pin+".mesurer()";
		

		return codePrefix + ret + codeSuffix;
	}
	
	
	
	
	
	
	
	
	
}
