package com.ardublock.translator.block.Duinoedu;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class SerialOscillo  extends TranslatorBlock {

	public SerialOscillo (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	//@Override
		public String toCode() throws SocketNullException, SubroutineNotDeclaredException
		{
			String Code;
			TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
			Code = translatorBlock.toCode();
			
			
			translator.addHeaderFile("SerialOSCILLO.h");
			
			translator.addDefinitionCommand("//libraries at http://www.duinoedu.com/");
			translator.addSetupCommand("SerialOscillo.begin(9600);");
			
			
			
			
			String ret =  "SerialOscillo.analogWrite("+ Code +");";
			
			return codePrefix +ret + codeSuffix;	
		}
}
