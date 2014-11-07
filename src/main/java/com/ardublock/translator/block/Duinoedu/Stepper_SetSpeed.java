package com.ardublock.translator.block.Duinoedu;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Stepper_SetSpeed  extends TranslatorBlock {

	public Stepper_SetSpeed (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	//@Override
		public String toCode() throws SocketNullException, SubroutineNotDeclaredException
		{
			String Pin1 ;
			String Pin2 ;
			String Pin3 ;
			String Pin4 ;
			String Speed;
			String Step;
			
			TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
			Pin1 = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
			Pin2 = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(2);
			Pin3 = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(3);
			Pin4 = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(4);
			Speed = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(5);
			Step = translatorBlock.toCode();
			
			
			String ret = "monMoteur"+Pin1+Pin2+Pin3+Pin4+".setSpeed("+Speed+");\n"+ "monMoteur"+Pin1+Pin2+Pin3+Pin4+".step("+Step+");";
			
			return codePrefix + ret + codeSuffix;
			
			
		
		}
}
