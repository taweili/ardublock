package com.ardublock.translator.block.Duinoedu;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Neopixel_init  extends TranslatorBlock {

	public Neopixel_init (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	//@Override
		public String toCode() throws SocketNullException, SubroutineNotDeclaredException
		{
			String Pin ;
			String NbLed;
			String NEO_KHZ800;
			String NEO_RGB;
			TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
			Pin = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
			NbLed = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(2);
			NEO_KHZ800 = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(3);
			NEO_RGB = translatorBlock.toCode();
			
			translator.addHeaderFile("Adafruit_NeoPixel.h");
			translator.addDefinitionCommand("//libraries at http://duinoedu.com/dl/lib/grove/EDU_Adafruit_NeoPixel_FlexStrip_Grove/ \nAdafruit_NeoPixel monRuban_pin"+Pin+" = Adafruit_NeoPixel("+NbLed+","+ Pin +", " +NEO_RGB+ " + "+NEO_KHZ800 +");");
			translator.addSetupCommand("monRuban_pin"+Pin+".brancher();\n" +
			"monRuban_pin"+Pin+".afficher();");
			
			
			return "" ;	
		}
}
