package com.ardublock.translator.block.keenlon;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.NumberBlock;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class EyeMaxIndexBlock extends TranslatorBlock
{
	public EyeMaxIndexBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		translator.addHeaderFile("keenlon.h");		
		translator.addDefinitionCommand("Eye " + "eye" + ";");	
		String ret = "eye.maxIndex()";

		return codePrefix + ret + codeSuffix;

	}	
}
