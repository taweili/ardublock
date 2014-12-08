package com.ardublock.translator.block.Duinoedu;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class HumiPro extends TranslatorBlock {
	public HumiPro(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String Pin;
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		Pin = translatorBlock.toCode();

		
		translator.addHeaderFile("DHT.h");
		translator.addSetupCommand("monDHTPRO_Humi_pin"+Pin+".begin();");
		translator.addDefinitionCommand("//libraries at http://duinoedu.com/dl/lib/grove/EDU_DHT_Grove/ \nDHT monDHTPRO_Humi_pin"+Pin+"("+Pin +",DHT22);"	);
		
		String ret = "monDHTPRO_Humi_pin"+Pin+".readHumidity()";
		

		return codePrefix + ret + codeSuffix;
	}
	
	
	
	
	
	
	
	
	
}
