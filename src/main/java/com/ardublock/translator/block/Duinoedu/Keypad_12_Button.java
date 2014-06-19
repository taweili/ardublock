package com.ardublock.translator.block.DuinoEDU;

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
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		StartPin = translatorBlock.toCode();
		String EndPin;
		TranslatorBlock translatorBlock2 = this.getRequiredTranslatorBlockAtSocket(1);
		EndPin = translatorBlock2.toCode();

		translator.addHeaderFile("Keypad.h");
		translator.addDefinitionCommand("//libraries at http://www.duinoedu.com/"	);
		translator.addSetupCommand("keypad.brancher("+StartPin +","+EndPin+");");
				
return "";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
