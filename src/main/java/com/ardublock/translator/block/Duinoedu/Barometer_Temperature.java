package com.ardublock.translator.block.Duinoedu;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Barometer_Temperature  extends TranslatorBlock {

	public Barometer_Temperature (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	//@Override
		public String toCode() throws SocketNullException, SubroutineNotDeclaredException
		{		
			translator.addHeaderFile("Wire.h");
			translator.addHeaderFile("Barometer.h");
			translator.addDefinitionCommand("Barometer monBarometre;");
			translator.addDefinitionCommand("//libraries at http://duinoedu.com/dl/lib/grove/EDU_BarometerSensor_Grove/");
			translator.addSetupCommand("monBarometre.brancher();");
			
			return codePrefix +"monBarometre.lireTemperature()" + codeSuffix;	
		}
}
