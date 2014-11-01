package com.ardublock.translator.block.Duinoedu;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Partie_Recepteur  extends TranslatorBlock {

	public Partie_Recepteur (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	//@Override
		public String toCode() throws SocketNullException, SubroutineNotDeclaredException
		{
			String Pin;
			TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
			Pin = translatorBlock.toCode();
			
			translator.addHeaderFile("VirtualWire.h");
			translator.addDefinitionCommand("//libraries at http://duinoedu.com/dl/lib/grove/EDU_VirtualWire_Grove/");
			translator.addSetupCommand("brancherRecepteur("+ Pin +");");
			return codePrefix +"\"recevoirTexte()\"" + codeSuffix;	
		}
}
