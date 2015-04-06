package com.ardublock.translator.block.Esplora;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;


public class TFT_Height extends TranslatorBlock {

	public TFT_Height (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	//@Override
			public String toCode() throws SocketNullException, SubroutineNotDeclaredException
			{
				
				
				translator.addHeaderFile("Esplora.h");
				translator.addHeaderFile("TFT.h");
				translator.addHeaderFile("SPI.h");
				String ret = "EsploraTFT.height()";
				
				return codePrefix + ret + codeSuffix;
					
			}
	
}
