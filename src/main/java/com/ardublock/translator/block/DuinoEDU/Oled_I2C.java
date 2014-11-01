package com.ardublock.translator.block.Duinoedu;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Oled_I2C  extends TranslatorBlock {

	public Oled_I2C (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	//@Override
		public String toCode() throws SocketNullException, SubroutineNotDeclaredException
		{
			String line_number;
			String brightness;
			TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
			line_number = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(2);
			brightness = translatorBlock.toCode();
			
			translator.addHeaderFile("Wire.h");
			translator.addHeaderFile("SeeedOLED.h");
			translator.addDefinitionCommand("//libraries at http://duinoedu.com/dl/lib/grove/EDU_OLED_Display12864_Grove/ ");
			translator.addSetupCommand("SeeedOled.brancher();\nSeeedOled.reglerLuminositeA("+ brightness +");\n ");
			
			
			TranslatorBlock translatorBlock2 = this.getRequiredTranslatorBlockAtSocket(0, "SeeedOled.ecrire(", " );\n");
			
			String ret = "SeeedOled.placerCurseurEn("+ line_number +");\n" +	translatorBlock2.toCode() ;
			
			return ret ;	
		}
}
