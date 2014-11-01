package com.ardublock.translator.block.Duinoedu;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Infrared extends TranslatorBlock {
	public Infrared(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String Pin;
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		Pin = translatorBlock.toCode();

		translator.addHeaderFile("IRremote.h");
		translator.addDefinitionCommand("//libraries at http://duinoedu.com/dl/lib/grove/EDU_IRremote_GroveDupont/\nIRrecv monRecepteur_pin"+Pin+"("+Pin +");"	);
		translator.addSetupCommand("monRecepteur_pin"+Pin+".enableIRIn(); ");

		String ret = "monRecepteur_pin"+Pin+".lireCodeIr() ";
		return codePrefix + ret + codeSuffix;
	}
	
	
	
	
	
	
	
	
	
}
