package com.ardublock.translator.block.Esplora;

import java.util.ResourceBundle;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class TFT_Load_Image extends TranslatorBlock
{
	private static ResourceBundle uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");
	
	public TFT_Load_Image(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(1);
		if (!(tb instanceof variable_PImage)) {
			throw new BlockException(blockId, uiMessageBundle.getString("ardublock.error_msg.string_var_slot"));
		}
		
		translator.addHeaderFile("Esplora.h");
		translator.addHeaderFile("SPI.h");
		translator.addHeaderFile("SD.h");
		translator.addHeaderFile("TFT.h");
		
		translator.addDefinitionCommand("#define SD_CS    8");

		
		String ret = tb.toCode();
		tb = this.getRequiredTranslatorBlockAtSocket(0);
		ret = ret + " = EsploraTFT.loadImage(" + tb.toCode() + ");\n";
		return ret;
	}

}
