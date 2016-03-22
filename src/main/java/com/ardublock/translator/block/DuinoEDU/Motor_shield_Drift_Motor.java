package com.ardublock.translator.block.Duinoedu;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Motor_shield_Drift_Motor  extends TranslatorBlock {

	public Motor_shield_Drift_Motor (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	//@Override
		public String toCode() throws SocketNullException, SubroutineNotDeclaredException
		{
			String Drift;
			
			TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
			Drift = translatorBlock.toCode();
			
			
			translator.addDefinitionCommand("//libraries at http://duinoedu.com/dl/lib/dupont/EDU_MotorShield_Dupont/");
			translator.addHeaderFile("MotorShieldDupont.h");
			translator.addDefinitionCommand("MotorShieldDupont monMotorShield;");
			translator.addSetupCommand("monMotorShield.brancher();");
			
						
			String ret =  "monMotorShield.ecrireDerive("+ Drift +");\n";
			
			return ret ;	
		}
}
