package com.ardublock.translator.block.Esplora;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;


public class TFT_Text extends TranslatorBlock {

	public TFT_Text (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	//@Override
			public String toCode() throws SocketNullException, SubroutineNotDeclaredException
			{
				String Message;
				String XPos;
				String YPos;
				String Height;
				String Red;
				String Green;
				String Blue;
							
				TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
				Message = translatorBlock.toCode();
				translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
				XPos = translatorBlock.toCode();
				translatorBlock = this.getRequiredTranslatorBlockAtSocket(2);
				YPos = translatorBlock.toCode();
				translatorBlock = this.getRequiredTranslatorBlockAtSocket(3);
				Height = translatorBlock.toCode();
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
				"EsploraTFT.setTextSize("+Height+");"+
				"EsploraTFT.text("+Message+","+XPos+","+YPos+");";
				
				return ret;
					
			}
	
}
