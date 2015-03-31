package com.ardublock.translator.block.Duinoedu;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Motor_shield_Speed_Motor  extends TranslatorBlock {

	public Motor_shield_Speed_Motor (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
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
			
			translator.addHeaderFile("MotorShieldDupont.h");
			translator.addDefinitionCommand("MotorShieldDupont monMotorShield;");
			translator.addSetupCommand("monMotorShield.brancher();");
			
						
			String ret =  "monMotorShield.ecrireVitesse("+ speed1 +","+ speed2+");\n";
			
			return ret ;	
		}
}
