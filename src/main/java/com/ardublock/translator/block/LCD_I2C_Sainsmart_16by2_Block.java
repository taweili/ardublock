package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class LCD_I2C_Sainsmart_16by2_Block extends TranslatorBlock {
	
	public LCD_I2C_Sainsmart_16by2_Block(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	//@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(1);
		String lineNo = tb.toCode();
		tb = this.getRequiredTranslatorBlockAtSocket(2);
		String charNo = tb.toCode();
		tb = this.getRequiredTranslatorBlockAtSocket(3);
		String I2C_addr = tb.toCode();
		
		String ret = "lcd_I2C_" + I2C_addr + ".setCursor( " + charNo + ", " + lineNo + " );";
		
		tb = this.getRequiredTranslatorBlockAtSocket(0, "lcd_I2C_" + I2C_addr + ".print( ", " );\n");
		ret += tb.toCode();
		//Deal with line and character positioning
		translator.addHeaderFile("Wire.h");
		translator.addHeaderFile("LCD.h");
		translator.addHeaderFile("LiquidCrystal_I2C.h");
		//											These are *NOT* Arduino pins. They indicate how the I2C interface is connected to the LCD display
		//																									v  v  v  v  v  v  v  v									

		translator.addDefinitionCommand("LiquidCrystal_I2C lcd_I2C_" + I2C_addr + "(0x" + I2C_addr + ", 2, 1, 0, 4, 5, 6, 7, 3, POSITIVE);");
		translator.addSetupCommand("lcd_I2C_" + I2C_addr + ".begin (16, 2);");
		translator.addSetupCommand("lcd_I2C_" + I2C_addr + ".setBacklight(HIGH);");
		
		return ret;
	}
	
}