package com.ardublock.translator.block.storage;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class MaSD_separator extends TranslatorBlock
{
	public MaSD_separator(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String Space;
		String Tab;
		
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		Space = translatorBlock.toCode();
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
		Tab = translatorBlock.toCode();
		
		translator.addHeaderFile("SD.h");
		translator.addHeaderFile("SDPlus.h");
		translator.addDefinitionCommand("//libraries at http://duinoedu.com/dl/lib/autre/EDU_SDPlus/\nSDPlus maSD;");
		String ret="maSD.espace("+Space+");\n"+
				"maSD.tabulation("+Tab+");";
		
		return  ret ;
	}
}
