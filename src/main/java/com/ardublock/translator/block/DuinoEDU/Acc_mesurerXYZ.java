package com.ardublock.translator.block.Duinoedu;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Acc_mesurerXYZ  extends TranslatorBlock {

	public Acc_mesurerXYZ (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	//@Override
		public String toCode() throws SocketNullException, SubroutineNotDeclaredException
		{
					
			translator.addHeaderFile("Wire.h");
			translator.addHeaderFile("MMA7660.h");
			translator.addDefinitionCommand("//libraries at http://duinoedu.com/dl/lib/grove/EDU_MMA7660_Grove/ \nMMA7660 monAccelerometre;");
			translator.addSetupCommand("monAccelerometre.brancher();");
			
						
			String ret =  "monAccelerometre.mesurerXYZ()";
			
			return codePrefix + ret + codeSuffix;
		}
}
