package com.ardublock.translator.block.Duinoedu;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Keypad_12_Button extends TranslatorBlock  {

	public Keypad_12_Button(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	@Override
		public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{

		String StartPin;
		String Diplay;
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		StartPin = translatorBlock.toCode();
		String EndPin;
		TranslatorBlock translatorBlock2 = this.getRequiredTranslatorBlockAtSocket(1);
		EndPin = translatorBlock2.toCode();
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(2);
		Diplay = translatorBlock.toCode();

		translator.addHeaderFile("Keypad.h");
		translator.addDefinitionCommand("//libraries at http://duinoedu.com/dl/lib/grove/EDU_Keypad_Dupont/ \nKeypad keypad;"	);
		translator.addSetupCommand("keypad.brancher("+StartPin +","+EndPin+");\n"+
				Diplay );
				
return "";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
