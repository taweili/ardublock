package com.ardublock.translator.block.Duinoedu;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Stepper_step  extends TranslatorBlock {

	public Stepper_step (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
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
			String Stepperround;
						
			TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
			Pin1 = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
			Pin2 = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(2);
			Pin3 = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(3);
			Pin4 = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(4);
			Stepperround = translatorBlock.toCode();
			
			
			translator.addHeaderFile("Stepper.h");
			translator.addDefinitionCommand("//libraries at http://duinoedu.com/dl/lib/dupont/EDU_Stepper_Dupont/ \nint nbrDePasParTour"+Pin1+" = "+Stepperround+";\n"+
			"Stepper monMoteur"+Pin1+Pin2+Pin3+Pin4+"(nbrDePasParTour"+Pin1+","+Pin1+","+Pin2+","+Pin3+","+Pin4+");");
			
			
			
			return "";
			
			
		
		}
}
