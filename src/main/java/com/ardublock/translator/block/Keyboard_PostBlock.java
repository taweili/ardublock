package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Keyboard_PostBlock  extends TranslatorBlock {
	public Keyboard_PostBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String DataPin;
		String IRQpin;
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		DataPin = translatorBlock.toCode();
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
		IRQpin = translatorBlock.toCode();
		
		translator.addHeaderFile("PS2Keyboard.h");
		translator.addSetupCommand("delay(1000);"
				+ "keyboard_pin"+DataPin+IRQpin+".brancher(DataPin, IRQpin);"

				+ "Serial.begin(9600);");
		
		translator.addDefinitionCommand("// Pin keyboard\n"
				+ "const int DataPin = " + DataPin +";\n"
				+ "const int IRQpin =  " + IRQpin + ";\n"
				+ "PS2Keyboard keyboard_pin"+DataPin+IRQpin+";\n"	);
		return codePrefix + "keyboard_pin"+DataPin+IRQpin+".afficher()"+ codeSuffix;
	}
	

}
