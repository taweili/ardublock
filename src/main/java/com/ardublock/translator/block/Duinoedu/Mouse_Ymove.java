package com.ardublock.translator.block.Duinoedu;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Mouse_Ymove  extends TranslatorBlock {

	public Mouse_Ymove(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
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
		translator.addDefinitionCommand("Mouse mouse_pin"+DataPin+Clk+";");

		
		translator.addSetupCommand("//libraries at http://www.duinoedu.com/ \n"
				+ "mouse_pin"+DataPin+Clk+".branch(" + DataPin
				+ "," + Clk + ");");

		return codePrefix + "mouse_pin"+DataPin+Clk+".read(\"y\")"+ codeSuffix;
	}
	
	
}
