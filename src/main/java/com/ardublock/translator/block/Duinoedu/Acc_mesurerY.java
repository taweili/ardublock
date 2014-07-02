package com.ardublock.translator.block.DuinoEDU;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Acc_mesurerY  extends TranslatorBlock {

	public Acc_mesurerY (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	//@Override
		public String toCode() throws SocketNullException, SubroutineNotDeclaredException
		{
					
			translator.addHeaderFile("Wire.h");
			translator.addHeaderFile("MMA7660.h");
			translator.addDefinitionCommand("//libraries at http://www.duinoedu.com/\nMMA7660 monAccelerometre;");
			translator.addSetupCommand("monAccelerometre.brancher();");
			
						
			String ret =  "monAccelerometre.mesurerY()";
			
			return codePrefix + ret + codeSuffix;
		}
}
