package com.ardublock.translator.block.Esplora;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;


public class INB extends TranslatorBlock {

	public INB (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	//@Override
			public String toCode() throws SocketNullException, SubroutineNotDeclaredException
			{
				
				translator.addDefinitionCommand("//Lib for INA INB at http://duinoedu.com/dl/lib/autre/EDU_Esplora/");
				translator.addHeaderFile("Esplora.h");
				
				
				return codePrefix + "Esplora.readInputB()" + codeSuffix;
					
			}
	
}
