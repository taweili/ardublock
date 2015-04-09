package com.ardublock.translator.block.Esplora;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class File_Close extends TranslatorBlock
{
	public File_Close(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode()  throws SocketNullException, SubroutineNotDeclaredException
	{
		
		String Variable;
		
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		Variable = translatorBlock.toCode();
		
		translator.addHeaderFile("Esplora.h");
		translator.addHeaderFile("SPI.h");
		translator.addHeaderFile("SD.h");
		translator.addDefinitionCommand("\tconst int chipSelect = 8;");
	    translator.addSetupCommand("SD.begin(chipSelect);");
	    
	    String ret = Variable + ".close();";
	    
		return ret;
	}

}
