package com.ardublock.translator.block.Esplora;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;


public class Mouse_Click extends TranslatorBlock {

	public Mouse_Click (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	//@Override
			public String toCode() throws SocketNullException, SubroutineNotDeclaredException
			{
				String Click;
				TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
				Click = translatorBlock.toCode();
				
				translator.addSetupCommand("Mouse.begin();");
				String ret = "Mouse.click(MOUSE_"+Click+");\n";
				
				return ret;
					
			}
	
}
