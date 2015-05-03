package com.ardublock.translator.block.Esplora;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;


public class TFT_Background extends TranslatorBlock {

	public TFT_Background (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	//@Override
			public String toCode() throws SocketNullException, SubroutineNotDeclaredException
			{
				String Red;
				String Green;
				String Blue;
							
				TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
				Red = translatorBlock.toCode();
				translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
				Green = translatorBlock.toCode();
				translatorBlock = this.getRequiredTranslatorBlockAtSocket(2);
				Blue = translatorBlock.toCode();
				
				translator.addHeaderFile("Esplora.h");
				translator.addHeaderFile("SPI.h");
				translator.addHeaderFile("SD.h");
				translator.addHeaderFile("TFT.h");
				
				translator.addSetupCommand("EsploraTFT.begin();");
				String ret = "EsploraTFT.background("+Blue+","+Green+","+Red+");";
				
				return ret;
					
			}
	
}
