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
			String Clk;
			String Dio;	
			String Display;
			String On_Digit;
			
			TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
			Clk = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
			Dio = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(2);
			Display = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(3);
			On_Digit = translatorBlock.toCode();
			
			translator.addHeaderFile("TM1637.h");
			translator.addDefinitionCommand("//libraries at http://duinoedu.com/dl/lib/grove/EDU_DigitalTube_Grove/ \n// Pin Led Bar\n"
					+ "TM1637 mesChiffres"+Clk+Dio+"(" + Clk
					+ "," + Dio + ");");
			translator.addSetupCommand("mesChiffres"+Clk+Dio+".brancher();\n");
			String ret;
						
				 ret =  "mesChiffres"+Clk+Dio+".afficherSur" + On_Digit + "("+ Display + ");\n";
		

			return ret ;	
		}
}
