package com.ardublock.translator.block.Duinoedu;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Serialplus_send extends TranslatorBlock {
	public Serialplus_send(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String Variable;
		String Canal;
		String Serial;
		String Interval;
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		Variable = translatorBlock.toCode();
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
		Canal = translatorBlock.toCode();
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(2);
		Serial = translatorBlock.toCode();
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(3);
		Interval = translatorBlock.toCode();

		translator.addHeaderFile("SerialPlus.h");
		translator.addDefinitionCommand("//libraries at http://duinoedu.com/dl/lib/autre/EDU_SerialPlus/");
		translator.addDefinitionCommand("SerialPlus mon"+Serial.replace("\"","")+"Plus;"	);
		translator.addSetupCommand("mon"+Serial.replace("\"","")+"Plus.branch(&"+Serial.replace("\"","")+"); \nmon"+Serial.replace("\"","")+"Plus.begin(9600);");
		
		String ret = " mon"+Serial.replace("\"","")+"Plus.print("+Variable+",CANAL"+Canal+","+Interval+");";

		return  ret;
	}
	
	
	
	
	
	
	
	
	
}
