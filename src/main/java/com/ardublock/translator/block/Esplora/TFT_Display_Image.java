package com.ardublock.translator.block.Esplora;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;


public class TFT_Display_Image extends TranslatorBlock {

	public TFT_Display_Image (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	//@Override
			public String toCode() throws SocketNullException, SubroutineNotDeclaredException
			{
				String Variable;
				String XStart;
				String YStart;
				
							
				TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
				Variable = translatorBlock.toCode();
				translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
				XStart = translatorBlock.toCode();
				translatorBlock = this.getRequiredTranslatorBlockAtSocket(2);
				YStart = translatorBlock.toCode();
								
				
				translator.addHeaderFile("Esplora.h");
				translator.addHeaderFile("SPI.h");
				translator.addHeaderFile("SD.h");
				translator.addHeaderFile("TFT.h");
				
				translator.addSetupCommand("EsploraTFT.begin();");
				String ret = " EsploraTFT.image("+Variable+","+XStart+","+YStart+");\n";
				
				return ret;
					
			}
	
}
