package com.ardublock.translator.block.Duinoedu;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Led_Bar extends TranslatorBlock {
	public Led_Bar(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String Clk;
		String Dio;
		String Niveau;
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		Dio = translatorBlock.toCode();
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
		Clk = translatorBlock.toCode();
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(2);
		Niveau = translatorBlock.toCode();
		
		
		translator.addHeaderFile("LED_Bar.h");
		
		
		translator.addDefinitionCommand("//libraries at http://duinoedu.com/dl/lib/grove/EDU_LedBar_Grove/ \n// Pin Led Bar\n"
				+ "LED_Bar mesLeds_pin"+Dio+Clk+"(" + Clk
				+ "," + Dio + ");");
		String ret = "mesLeds_pin"+Dio+Clk+".displayVoltage("+ Niveau +");\n";
		
		return codePrefix + ret + codeSuffix;
	}
	
	
	
	
	
	
	
	
	
}
