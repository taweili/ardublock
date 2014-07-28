package com.ardublock.translator.block.DuinoEDU;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Rfid_Test extends TranslatorBlock {
	public Rfid_Test(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{

		String Code;
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		Code = translatorBlock.toCode();

		translator.addHeaderFile("RFID125.h");
		translator.addHeaderFile("SoftwareSerial.h");
		translator.addDefinitionCommand("//libraries at http://www.duinoedu.com/\nRFID125 monRFID;");
		String ret = "monRFID.testerCode("+Code+")";
		return codePrefix + ret + codeSuffix;
	}
	
	
	
	
	
	
	
	
	
}
