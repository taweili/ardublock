package com.ardublock.translator.block.Esplora;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;


public class TFT_Line extends TranslatorBlock {

	public TFT_Line (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	//@Override
			public String toCode() throws SocketNullException, SubroutineNotDeclaredException
			{
				String XStart;
				String YStart;
				String XEnd;
				String YEnd;
				String Red;
				String Green;
				String Blue;
							
				TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
				XStart = translatorBlock.toCode();
				translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
				YStart = translatorBlock.toCode();
				translatorBlock = this.getRequiredTranslatorBlockAtSocket(2);
				XEnd = translatorBlock.toCode();
				translatorBlock = this.getRequiredTranslatorBlockAtSocket(3);
				YEnd = translatorBlock.toCode();
				translatorBlock = this.getRequiredTranslatorBlockAtSocket(4);
				Red = translatorBlock.toCode();
				translatorBlock = this.getRequiredTranslatorBlockAtSocket(5);
				Green = translatorBlock.toCode();
				translatorBlock = this.getRequiredTranslatorBlockAtSocket(6);
				Blue = translatorBlock.toCode();
				
				
				
				translator.addHeaderFile("Esplora.h");
				translator.addHeaderFile("SPI.h");
				translator.addHeaderFile("SD.h");
				translator.addHeaderFile("TFT.h");
				
				translator.addSetupCommand("EsploraTFT.begin();");
				String ret = "EsploraTFT.stroke("+Blue+","+Green+","+Red+");\n"+
				"EsploraTFT.line("+XStart+","+YStart+","+XEnd+","+YEnd+");";
				
				return ret;
					
			}
	
}
