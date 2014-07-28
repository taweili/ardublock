package com.ardublock.translator.block.DuinoEDU;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Driver_I2C_Speed_A_Motor  extends TranslatorBlock {

	public Driver_I2C_Speed_A_Motor (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	//@Override
		public String toCode() throws SocketNullException, SubroutineNotDeclaredException
		{
			String speed;
			TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
			speed = translatorBlock.toCode();
			
			
			translator.addHeaderFile("Wire.h");
			translator.addHeaderFile("MotorI2C.h");
			translator.addDefinitionCommand("//libraries at http://www.duinoedu.com/\nMotorI2C mesMoteurs;");
			translator.addSetupCommand("mesMoteurs.brancher();");
			
						
			String ret =  "mesMoteurs.ecrireVitesseMoteurA("+ speed +");\n";
			
			return ret ;	
		}
}
