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
			String Dial;
			String Value;
			TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
			Code = translatorBlock.toCode();
			TranslatorBlock translatorBlock1 = this.getRequiredTranslatorBlockAtSocket(1);
			Dial = translatorBlock1.toCode();
			TranslatorBlock translatorBlock2 = this.getRequiredTranslatorBlockAtSocket(2);
			Value = translatorBlock2.toCode();
			
			
			
			
			String ret =  "SerialOscillo.analogWrite("+ Code +",SUR_CADRAN"+Dial +","+Value +");";
			
			return codePrefix +ret + codeSuffix;	
		}
}
