package com.ardublock.translator.block.Esplora;

import java.util.ResourceBundle;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Setter_Variable_File extends TranslatorBlock
{
	private static ResourceBundle uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");
	
	public Setter_Variable_File(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);
		if (!(tb instanceof Variable_File)) {
			throw new BlockException(blockId, uiMessageBundle.getString("ardublock.error_msg.string_var_slot"));
		}
		
		translator.addHeaderFile("Esplora.h");
		translator.addHeaderFile("SPI.h");
		translator.addHeaderFile("SD.h");
		translator.addHeaderFile("TFT.h");
		translator.addDefinitionCommand("\tconst int chipSelect = 8;");
	    translator.addSetupCommand("SD.begin(chipSelect);");
	    
		String ret = tb.toCode();
		tb = this.getRequiredTranslatorBlockAtSocket(1);
		ret = ret + " = SD.open(" + tb.toCode() + ");\n";
		return ret;
	}

}
