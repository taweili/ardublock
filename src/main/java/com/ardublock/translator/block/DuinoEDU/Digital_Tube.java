package com.ardublock.translator.block.Duinoedu;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Digital_Tube  extends TranslatorBlock {

	public Digital_Tube (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	//@Override
		public String toCode() throws SocketNullException, SubroutineNotDeclaredException
		{
			String Clk;
			String Dio;
			String Brightness;
			String isMatch;
			TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
			Clk = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
			Dio = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(2);
			Brightness = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(3);
			isMatch=translatorBlock.toCode();
			
			translator.addHeaderFile("TM1637.h");
			translator.addDefinitionCommand("//libraries at http://duinoedu.com/dl/lib/grove/EDU_DigitalTube_Grove/ \n// Pin Led Bar\n"
					+ "TM1637 mesChiffres"+Clk+Dio+"(" + Clk
					+ "," + Dio + ");");
			translator.addSetupCommand("mesChiffres"+Clk+Dio+".brancher();\n"+
					"mesChiffres"+Clk+Dio+".luminosite("+Brightness +");\n");
			
			String ret;
			
			 ret =  "mesChiffres"+Clk+Dio+".point("+isMatch+");";
	

		return ret ;	
		}
}
