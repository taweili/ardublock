package com.ardublock.translator.block;


import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class BlunoOledPrintlnBlock extends TranslatorBlock
{
	public BlunoOledPrintlnBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		translator.addHeaderFile("Wire.h");
		translator.addHeaderFile("BlunoOled00.h");
		
		translator.addDefinitionCommand("BlunoOled oled;\n");
		
		translator.addSetupCommand("oled.oled_init();\n");
		
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		
		String ret = "oled.print(" + translatorBlock.toCode() + ");\n";
		return ret;
	}
}
