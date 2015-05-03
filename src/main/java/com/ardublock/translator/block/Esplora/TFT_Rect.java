package com.ardublock.translator.block.Esplora;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;


public class TFT_Rect extends TranslatorBlock {

	public TFT_Rect (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	//@Override
			public String toCode() throws SocketNullException, SubroutineNotDeclaredException
			{
				String XPos;
				String YPos;
				String Height;
				String Width;
				String Fill;
				String Red_Fill;
				String Green_Fill;
				String Blue_Fill;
				String Stroke;
				String Red_Stroke;
				String Green_Stroke;
				String Blue_Stroke;
							
				TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
				XPos = translatorBlock.toCode();
				translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
				YPos = translatorBlock.toCode();
				translatorBlock = this.getRequiredTranslatorBlockAtSocket(2);
				Width = translatorBlock.toCode();
				translatorBlock = this.getRequiredTranslatorBlockAtSocket(3);
				Height = translatorBlock.toCode();
				translatorBlock = this.getRequiredTranslatorBlockAtSocket(4);
				Fill = translatorBlock.toCode();
				translatorBlock = this.getRequiredTranslatorBlockAtSocket(5);
				Red_Fill = translatorBlock.toCode();
				translatorBlock = this.getRequiredTranslatorBlockAtSocket(6);
				Green_Fill = translatorBlock.toCode();
				translatorBlock = this.getRequiredTranslatorBlockAtSocket(7);
				Blue_Fill = translatorBlock.toCode();
				translatorBlock = this.getRequiredTranslatorBlockAtSocket(8);
				Stroke = translatorBlock.toCode();
				translatorBlock = this.getRequiredTranslatorBlockAtSocket(9);
				Red_Stroke = translatorBlock.toCode();
				translatorBlock = this.getRequiredTranslatorBlockAtSocket(10);
				Green_Stroke = translatorBlock.toCode();
				translatorBlock = this.getRequiredTranslatorBlockAtSocket(11);
				Blue_Stroke = translatorBlock.toCode();
				
				translator.addHeaderFile("Esplora.h");
				translator.addHeaderFile("SPI.h");
				translator.addHeaderFile("SD.h");
				translator.addHeaderFile("TFT.h");
				
				translator.addSetupCommand("EsploraTFT.begin();");
				String ret ="";
				
				if (Stroke.equals("true")) {
					ret += "EsploraTFT.stroke("+Blue_Stroke+","+Green_Stroke+","+Red_Stroke+");\n";
				}
				else
				{
					ret += "EsploraTFT.noStroke();\n";
				}
				if (Fill.equals("true")) {
					ret +="EsploraTFT.fill("+Blue_Fill+","+Green_Fill+","+Red_Fill+");\n";
				}
				else
				{
					ret += "EsploraTFT.noFill();\n";
				}
				ret +="EsploraTFT.rect("+XPos+","+YPos+","+Width+","+Height+");";
				
				return ret;
					
			}
			
}
