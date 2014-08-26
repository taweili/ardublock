package com.ardublock.translator.block.Duinoedu;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Digital_Tube_Display2  extends TranslatorBlock {

	public Digital_Tube_Display2 (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	//@Override
		public String toCode() throws SocketNullException, SubroutineNotDeclaredException
		{
			
			String On_Digit1;
			String On_Digit2;
			String On_Digit3;
			String On_Digit4;
			String isMatch;
			TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
			On_Digit1 = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
			On_Digit2 = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(2);
			On_Digit3 = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(3);
			On_Digit4 = translatorBlock.toCode();
			 
			String ret;
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(4);
			isMatch=translatorBlock.toCode();
			
			
				 ret =  "mesChiffres.afficherSur1("+ On_Digit1 + ");\n"+
						"mesChiffres.afficherSur2("+ On_Digit2 + ");\n"+
						"mesChiffres.afficherSur3("+ On_Digit3 + ");\n"+
						"mesChiffres.afficherSur4("+ On_Digit4 + ");\n"+
						"mesChiffres.point("+isMatch+");";
		

			return ret ;	
		}
}
