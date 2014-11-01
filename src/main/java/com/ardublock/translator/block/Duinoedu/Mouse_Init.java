package com.ardublock.translator.block.Duinoedu;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Mouse_Init extends TranslatorBlock {
	public Mouse_Init(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String DataPin;
		String Clk;
		
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		DataPin = translatorBlock.toCode();
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
		Clk = translatorBlock.toCode();
		
		
		translator.addHeaderFile("Mouse.h");
		translator.addDefinitionCommand("Mouse mouse;");

		
		translator.addSetupCommand("//libraries at http://duinoedu.com/dl/lib/grove/EDU_Mouse_Grove/ \n"
				+ "mouse.branch(" + DataPin
				+ "," + Clk + ");");

		
		return "";
	}
	
	
	
	
	
	
	
	
	
}
