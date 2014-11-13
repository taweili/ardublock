package com.ardublock.translator.block.Duinoedu;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Shildbot_Speed_Motor  extends TranslatorBlock {

	public Shildbot_Speed_Motor (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	//@Override
		public String toCode() throws SocketNullException, SubroutineNotDeclaredException
		{
			String speed1;
			String speed2;
			TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
			speed1 = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
			speed2 = translatorBlock.toCode();
			
			translator.addHeaderFile("Shieldbot.h");
			translator.addDefinitionCommand("//libraries at http://duinoedu.com/dl/lib/grove/EDU_Shieldbot_Grove/ \nShieldbot monBot;");
			translator.addSetupCommand("monBot.brancher();");
			
						
			String ret =  "monBot.ecrireVitesse("+ speed1 +","+ speed2+");\n";
			
			return ret ;	
		}
}
