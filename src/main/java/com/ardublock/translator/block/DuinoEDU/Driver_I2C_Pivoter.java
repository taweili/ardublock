package com.ardublock.translator.block.Duinoedu;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Driver_I2C_Pivoter  extends TranslatorBlock {

	public Driver_I2C_Pivoter (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	//@Override
		public String toCode() throws SocketNullException, SubroutineNotDeclaredException
		{
			String way;
			TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
			way = translatorBlock.toCode();
			
			
			translator.addHeaderFile("Wire.h");
			translator.addHeaderFile("MotorI2C.h");
			translator.addDefinitionCommand("//libraries at http://www.duinoedu.com/\nMotorI2C mesMoteurs;");
			translator.addSetupCommand("mesMoteurs.brancher();");
			
						
			String ret =  "mesMoteurs.pivoter(\""+ way +"\");\n";
			
			return ret ;	
		}
}
