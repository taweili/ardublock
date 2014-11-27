package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Keyboard_Compare  extends TranslatorBlock {
	public Keyboard_Compare(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String DataPin;
		String IRQpin;
		String Touche;
		String Compare;
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		DataPin = translatorBlock.toCode();
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
		IRQpin = translatorBlock.toCode();
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(2);
		Compare = translatorBlock.toCode();
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(3);
		Touche = translatorBlock.toCode();
		
		
		translator.addHeaderFile("PS2Keyboard.h");
		translator.addSetupCommand("delay(1000);"
				+ "keyboard_pin"+DataPin+IRQpin+".brancher(DataPin, IRQpin);"

				+ "Serial.begin(9600);");
		
		translator.addDefinitionCommand("// Pin keyboard\n"
				+ "const int DataPin = " + DataPin +";\n"
				+ "const int IRQpin =  " + IRQpin + ";\n"
				+ "PS2Keyboard keyboard_pin"+DataPin+IRQpin+";\n"	);
		return codePrefix + "(String)"+Touche+Compare+"(String)keyboard_pin"+DataPin+IRQpin+".afficher()"+ codeSuffix;
	}
	

}
