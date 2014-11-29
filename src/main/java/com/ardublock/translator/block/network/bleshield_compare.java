package com.ardublock.translator.block.network;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class bleshield_compare extends TranslatorBlock {

	public bleshield_compare(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException {
		String DataPin;
		String IRQpin;
		String Touche;
		String Compare;
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		DataPin = translatorBlock.toCode();
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
		IRQpin = translatorBlock.toCode();
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(2);
		Compare = translatorBlock.toCode();
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(3);
		Touche = translatorBlock.toCode();
		translator.addHeaderFile("SoftwareSerial.h");
		translator.addDefinitionCommand("SoftwareSerial bleShield("+DataPin+","+ IRQpin+");");
		translator.addDefinitionCommand(bleshieldFunctions);
		translator.addSetupCommand("bleShield.begin(19200);");
		
		return codePrefix + "(word)"+Touche+Compare+"w"+ codeSuffix;

	}

	private static final String bleshieldFunctions = 
			"word w;"+
			"void recevoir(){\n" + 
			"	 if (bleShield.available()) {\n" + 
			"w=bleShield.read();" + 
			"}\n\n"+
			"}\n\n";	
	
}
