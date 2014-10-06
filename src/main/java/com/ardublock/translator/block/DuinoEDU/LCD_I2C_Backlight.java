package com.ardublock.translator.block.Duinoedu;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class LCD_I2C_Backlight  extends TranslatorBlock {

	public LCD_I2C_Backlight (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	//@Override
		public String toCode() throws SocketNullException, SubroutineNotDeclaredException
		{
			String R;
			String G;
			String B;
			TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
			R = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
			G = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(2);
			B = translatorBlock.toCode();
			
			
			String ret =  "monRgb.retroeclairage(" + R + ","+ G + "," + B + ");" ;
			
			return ret ;	
		}
}
