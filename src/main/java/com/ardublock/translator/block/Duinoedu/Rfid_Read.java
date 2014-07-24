package com.ardublock.translator.block.DuinoEDU;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Rfid_Read extends TranslatorBlock {
	public Rfid_Read(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String Pin1;
		String Pin2;
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		Pin1 = translatorBlock.toCode();
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
		Pin2 = translatorBlock.toCode();


		translator.addHeaderFile("RFID125.h");
		translator.addHeaderFile("SoftwareSerial.h");
		translator.addDefinitionCommand("//libraries at http://www.duinoedu.com/\nRFID125 monRFID_pin"+Pin1+Pin2+";");
		translator.addSetupCommand("monRFID_pin"+Pin1+Pin2+".brancher(" + Pin1+ "," + Pin2 + ");");
		String ret = "monRFID_pin"+Pin1+Pin2+".lireCode();";
		return codePrefix + ret + codeSuffix;
	}
	
	
	
	
	
	
	
	
	
}
