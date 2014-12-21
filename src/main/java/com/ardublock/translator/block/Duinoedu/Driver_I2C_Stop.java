package com.ardublock.translator.block.Duinoedu;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Driver_I2C_Stop  extends TranslatorBlock {

	public Driver_I2C_Stop (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	//@Override
		public String toCode() throws SocketNullException, SubroutineNotDeclaredException
		{
			
			
			
			translator.addHeaderFile("Wire.h");
			translator.addHeaderFile("MotorI2C.h");
			translator.addDefinitionCommand("//libraries athttp://duinoedu.com/dl/lib/grove/EDU_MotorI2C/ \nMotorI2C mesMoteurs;");
			translator.addSetupCommand("mesMoteurs.brancher();");
			
						
			String ret =  "mesMoteurs.stopper();\n";
			
			return ret ;	
		}
}
