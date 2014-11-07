package com.ardublock.translator.block.Duinoedu;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Nunchuck_AccelerationZ  extends TranslatorBlock {

	public Nunchuck_AccelerationZ (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	//@Override
		public String toCode() throws SocketNullException, SubroutineNotDeclaredException
		{		
			translator.addHeaderFile("Wire.h");
			translator.addHeaderFile("Nunchuck.h");
			translator.addDefinitionCommand("//libraries at http://www.duinoedu.com/");
			translator.addSetupCommand("monNunchuck.brancher();");
			
			return codePrefix +"monNunchuck.lireAccelerationZ()" + codeSuffix;	
		}
}
