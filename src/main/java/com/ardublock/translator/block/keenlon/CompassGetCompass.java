package com.ardublock.translator.block.keenlon;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.NumberBlock;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class CompassGetCompass extends TranslatorBlock
{
	public CompassGetCompass(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		translator.addHeaderFile("keenlon.h");
		translator.addSetupCommand("comp.init();");
		translator.addDefinitionCommand("Compass " + "comp" + ";");
		
		String ret = "comp.getCompass()";

		return codePrefix + ret + codeSuffix;

	}	
}
