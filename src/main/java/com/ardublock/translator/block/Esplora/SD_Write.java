package com.ardublock.translator.block.Esplora;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class SD_Write extends TranslatorBlock
{
	public SD_Write(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		translator.addHeaderFile("Esplora.h");
		translator.addHeaderFile("SPI.h");
		translator.addHeaderFile("SD.h");
		translator.addHeaderFile("TFT.h");
		translator.addDefinitionCommand("\tconst int chipSelect = 8;");
	    translator.addSetupCommand("SD.begin(chipSelect);");
	    
	    TranslatorBlock data = this.getRequiredTranslatorBlockAtSocket(0);
		String ret="\tFile datafile = SD.open(\""+data.toCode()+"\", FILE_WRITE);\n";
		ret+="\tif(datafile){\n";
        data=this.getRequiredTranslatorBlockAtSocket(1, "\t\tdatafile.print( ", " );\n\t\tdatafile.print(\" \");\n");
        ret+=data.toCode();
        data=this.getRequiredTranslatorBlockAtSocket(2);
        String test=data.toCode();
		if(test.equals("true")){
		    ret+="\t\tdatafile.println(\"\");\n";
		}
		ret+="\t\tdatafile.close();\n";
        ret+="\t}\n";
		return  ret ;
	}
}
