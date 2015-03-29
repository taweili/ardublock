package com.ardublock.translator.block.Duinoedu;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class LCD_I2C_RGB2  extends TranslatorBlock {

	public LCD_I2C_RGB2 (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	//@Override
		public String toCode() throws SocketNullException, SubroutineNotDeclaredException
		{
			String line_number;
			String row_number;
			TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
			line_number = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(2);
			row_number = translatorBlock.toCode();
			
			translator.addHeaderFile("Wire.h");
			translator.addHeaderFile("rgb_lcd.h");
			translator.addDefinitionCommand("//libraries at http://duinoedu.com/dl/lib/grove/EDU_LCD_RGB_Backlight_Grove/ \nrgb_lcd monRgb;");
			translator.addSetupCommand("monRgb.branch();");
			
			
			TranslatorBlock translatorBlock2 = this.getRequiredTranslatorBlockAtSocket(0, "monRgb.ecrire(", " );\n");
			
			String ret =  "monRgb.placerCurseurEn("+ line_number +","+row_number+");\n" + translatorBlock2.toCode() ;
			
			return ret ;	
		}
}
