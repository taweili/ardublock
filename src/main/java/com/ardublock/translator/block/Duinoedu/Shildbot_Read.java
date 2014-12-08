package com.ardublock.translator.block.Duinoedu;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Shildbot_Read  extends TranslatorBlock {

	public Shildbot_Read (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	//@Override
		public String toCode() throws SocketNullException, SubroutineNotDeclaredException
		{
			String way;
			TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
			way = translatorBlock.toCode();
			
			
			translator.addHeaderFile("Shieldbot.h");
			translator.addDefinitionCommand("//libraries at http://duinoedu.com/dl/lib/grove/EDU_Shieldbot_Grove/ \nShieldbot monBot;");
			translator.addSetupCommand("monBot.brancher();");
						
			String ret =  "monBot.lire(\""+ way +"\")\n";
			
			return codePrefix + ret + codeSuffix;	
		}
}
