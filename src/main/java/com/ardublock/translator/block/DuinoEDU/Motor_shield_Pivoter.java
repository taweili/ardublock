package com.ardublock.translator.block.Duinoedu;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Motor_shield_Pivoter  extends TranslatorBlock {

	public Motor_shield_Pivoter (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	//@Override
		public String toCode() throws SocketNullException, SubroutineNotDeclaredException
		{
			String way;
			TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
			way = translatorBlock.toCode();
			
			translator.addDefinitionCommand("//libraries at http://duinoedu.com/dl/lib/dupont/EDU_MotorShield_Dupont/");
			translator.addHeaderFile("MotorShieldDupont.h");
			translator.addDefinitionCommand("MotorShieldDupont monMotorShield;");
			translator.addSetupCommand("monMotorShield.brancher();");
						
			String ret =  "monMotorShield.pivoter(\""+ way +"\");\n";
			
			return ret ;	
		}
}
