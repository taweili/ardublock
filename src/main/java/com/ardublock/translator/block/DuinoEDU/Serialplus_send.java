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
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		Variable = translatorBlock.toCode();
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
		Canal = translatorBlock.toCode();
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(2);
		Serial = translatorBlock.toCode();

		
		translator.addHeaderFile("SerialPlus.h");
		translator.addDefinitionCommand("SerialPlus monSerialPlus;"	);
		translator.addSetupCommand("monSerialPlus.branch(&"+Serial.replace("\"","")+"); \nmonSerialPlus.begin(9600);");
		
		String ret = " monSerialPlus.print("+Variable+",CANAL"+Canal+");";

		return  ret;
	}
	
	
	
	
	
	
	
	
	
}
