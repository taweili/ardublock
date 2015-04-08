package com.ardublock.translator.block.Esplora;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;

public class Variable_File_Bool extends TranslatorBlock
{
	public Variable_File_Bool(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode()
	{
		translator.addHeaderFile("Esplora.h");
		translator.addHeaderFile("SPI.h");
		translator.addHeaderFile("SD.h");
	    translator.addSetupCommand("\tconst int chipSelect = 8;\n\tSD.begin(chipSelect);\n");
		
		return codePrefix + label + codeSuffix;
	}

}
