package com.ardublock.translator.block.Duinoedu;


import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Digital_Clear extends TranslatorBlock {

	public Digital_Clear (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	//@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
				
		
		
		String ret = "mesChiffres.effacer();";
		

		return  ret ;	
	}
	
	
	
	
}
