package com.ardublock.translator.block.Duinoedu;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class LCD_I2C_RGB  extends TranslatorBlock {

	public LCD_I2C_RGB (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	//@Override
		public String toCode() throws SocketNullException, SubroutineNotDeclaredException
		{
			String line_number;
			TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
			line_number = translatorBlock.toCode();
			
			translator.addHeaderFile("Wire.h");
			translator.addHeaderFile("rgb_lcd.h");
			translator.addDefinitionCommand("//libraries at http://www.duinoedu.com/\nrgb_lcd monRgb;");
			translator.addSetupCommand("monRgb.branch();");
			
			
			TranslatorBlock translatorBlock2 = this.getRequiredTranslatorBlockAtSocket(0, "monRgb.ecrire(", " );\n");
			
			String ret =  "monRgb.placerCurseurEn("+ line_number +");\n" + translatorBlock2.toCode() ;
			
			return ret ;	
		}
}
