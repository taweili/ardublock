package com.ardublock.translator.block.Duinoedu;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Digital_Tube_Display  extends TranslatorBlock {

	public Digital_Tube_Display (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	//@Override
		public String toCode() throws SocketNullException, SubroutineNotDeclaredException
		{
			String Display;
			String On_Digit;
			String isMatch;
			TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
			Display = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
			On_Digit = translatorBlock.toCode();
			 
			String ret;
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(2);
			isMatch=translatorBlock.toCode();
			
			
				 ret =  "mesChiffres.afficherSur" + On_Digit + "("+ Display + ");\n"+
						"mesChiffres.point("+isMatch+");";
		

			return ret ;	
		}
}
