package com.ardublock.translator.block.Duinoedu;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Partie_Emetteur  extends TranslatorBlock {

	public Partie_Emetteur (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	//@Override
		public String toCode() throws SocketNullException, SubroutineNotDeclaredException
		{
			
			String Pin;
			TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
			Pin = translatorBlock.toCode();
			
			translator.addHeaderFile("VirtualWire.h");
			translator.addDefinitionCommand("//libraries at http://duinoedu.com/dl/lib/grove/EDU_VirtualWire_Grove/");
			translator.addSetupCommand("brancherEmetteur("+ Pin +");");
			
			
			TranslatorBlock translatorBlock2 = this.getRequiredTranslatorBlockAtSocket(0, "emettreTexte(", ");\n");
			
			String ret =  translatorBlock2.toCode() ;
			
			return ret ;	
		}
}
