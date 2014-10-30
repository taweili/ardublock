package com.ardublock.translator.block.Duinoedu;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Digital_Tube_Ecrire  extends TranslatorBlock {

	public Digital_Tube_Ecrire (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	//@Override
		public String toCode() throws SocketNullException, SubroutineNotDeclaredException
		{
			String Display;
			TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
			Display = translatorBlock.toCode();
			 
			String ret;
			ret =  "mesChiffres.ecrire("+Display +");";
		

			return ret ;	
		}
}
