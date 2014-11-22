package com.ardublock.translator.block.Duinoedu;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Shildbot_Stop  extends TranslatorBlock {

	public Shildbot_Stop (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	//@Override
		public String toCode() throws SocketNullException, SubroutineNotDeclaredException
		{
			
			
			
			translator.addHeaderFile("Shieldbot.h");
			translator.addDefinitionCommand("//libraries at http://duinoedu.com/dl/lib/grove/EDU_Shieldbot_Grove/ \nShieldbot monBot;");
			translator.addSetupCommand("monBot.brancher();");
						
			String ret =  "monBot.stopper();";
			
			return ret ;	
		}
}
