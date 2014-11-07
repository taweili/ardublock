package com.ardublock.translator.block.Duinoedu;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class SerialOscillo_Init  extends TranslatorBlock {

	public SerialOscillo_Init (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	//@Override
		public String toCode() throws SocketNullException, SubroutineNotDeclaredException
		{
			String Nb_Dial;
			TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
			Nb_Dial = translatorBlock.toCode();
			
			
			translator.addHeaderFile("SerialOSCILLO.h");
			
			translator.addDefinitionCommand("//libraries at http://www.duinoedu.com/");
			translator.addSetupCommand("SerialOscillo.begin(115200);\nSerialOscillo.nombreCadrans("+Nb_Dial+");");
			
			
			
			
			return "";	
		}
}
