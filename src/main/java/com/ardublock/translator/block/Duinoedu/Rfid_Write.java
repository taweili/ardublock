package com.ardublock.translator.block.Duinoedu;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Rfid_Write extends TranslatorBlock {
	public Rfid_Write(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String Pin1;
		String Pin2;
		String Code;
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		Pin1 = translatorBlock.toCode();
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
		Pin2 = translatorBlock.toCode();
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(2);
		Code = translatorBlock.toCode();


		translator.addHeaderFile("RFID125.h");
		translator.addHeaderFile("SoftwareSerial.h");
		translator.addDefinitionCommand("//libraries at http://duinoedu.com/dl/lib/grove/EDU_RFID125_Grove/ \nRFID125 monRFID;");
		translator.addSetupCommand("monRFID.brancher(" + Pin1+ "," + Pin2 + ");");
		String ret = "monRFID.ecrireCode("+Code+");\n";
		return codePrefix + ret + codeSuffix;
	}
	
	
	
	
	
	
	
	
	
}
