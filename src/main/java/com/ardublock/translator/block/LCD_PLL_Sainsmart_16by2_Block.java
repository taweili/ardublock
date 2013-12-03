package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class LCD_PLL_Sainsmart_16by2_Block extends TranslatorBlock {
	
	public LCD_PLL_Sainsmart_16by2_Block(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
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
		String parallel_addr = tb.toCode(); //Usually resolves to "Parallel" but could be something else.
		
		String ret = "";
		if ( !(charNo.equals("0") && lineNo.equals("0")) ){
			// Retain the apparently daft 'lcd_I2C' part of the name so LCD_COMMAND block will work on this display too.
			ret = "lcd_I2C_" + parallel_addr + ".setCursor( (" + charNo + ") - 1, (" + lineNo + ") - 1 );";
		}
		
		tb = this.getRequiredTranslatorBlockAtSocket(0, "lcd_I2C_"+ parallel_addr + ".print( ", " );\n");
		ret += tb.toCode();
		//Deal with line and character positioning
		translator.addHeaderFile("Wire.h");
		translator.addHeaderFile("LCD.h");
		translator.addHeaderFile("LiquidCrystal.h");
		translator.addDefinitionCommand(             "// For these LCD controls to work you MUST replace the standard LCD library with 'New LCD' from...");
		translator.addDefinitionCommand(			 "// https://bitbucket.org/fmalpartida/new-liquidcrystal/wiki/Home");
		translator.addDefinitionCommand(			 "// Direct download https://bitbucket.org/fmalpartida/new-liquidcrystal/downloads/LiquidCrystal_V1.2.1.zip");
		translator.addDefinitionCommand(             "// Your project will not compile until this is done.");
		translator.addDefinitionCommand(             "//");
		translator.addDefinitionCommand(             "//                             RS  EN  d0  d1  d2  d3  LED ");
		translator.addDefinitionCommand("LiquidCrystal lcd_I2C_" + parallel_addr + "(12, 11,  5,  4,  3,  2,  7, POSITIVE);");
		translator.addSetupCommand("lcd_I2C_" + parallel_addr + ".begin (16, 2);");
		translator.addSetupCommand("lcd_I2C_" + parallel_addr + ".setBacklight(HIGH);");
		
		return ret;
	}
	
}