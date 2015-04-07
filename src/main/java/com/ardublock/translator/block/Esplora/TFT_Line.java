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
				String XPos;
				String YPos;
				String Height;
				String Red;
				String Green;
				String Blue;
							
				TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
				XPos = translatorBlock.toCode();
				translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
				YPos = translatorBlock.toCode();
				translatorBlock = this.getRequiredTranslatorBlockAtSocket(2);
				Height = translatorBlock.toCode();
				translatorBlock = this.getRequiredTranslatorBlockAtSocket(3);
				Red = translatorBlock.toCode();
				translatorBlock = this.getRequiredTranslatorBlockAtSocket(4);
				Green = translatorBlock.toCode();
				translatorBlock = this.getRequiredTranslatorBlockAtSocket(5);
				Blue = translatorBlock.toCode();
				
				
				
				translator.addHeaderFile("Esplora.h");
				translator.addHeaderFile("TFT.h");
				translator.addHeaderFile("SPI.h");
				
				translator.addSetupCommand("EsploraTFT.begin();");
				String ret = "EsploraTFT.stroke("+Red+","+Green+","+Blue+");\n"+
				"EsploraTFT.line("+XPos+","+YPos+","+Height+");";
				
				return ret;
					
			}
	
}
