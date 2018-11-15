package com.ardublock.translator.block.u8x2display;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.NumberBlock;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;



public class Oled96Inch128x64SSD1306I2CNONAMEHWBlock  extends TranslatorBlock
{
	public Oled96Inch128x64SSD1306I2CNONAMEHWBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{

		translator.addHeaderFile("Arduino.h");
		translator.addHeaderFile("U8x8lib.h");
		translator.addDefinitionCommand("U8X8_SSD1306_128X64_NONAME_HW_I2C u8x8(U8X8_PIN_NONE);");
		translator.addSetupCommand("u8x8.begin();");

		return "";
	}
}
