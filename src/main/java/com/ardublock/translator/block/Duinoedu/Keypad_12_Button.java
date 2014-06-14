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
		translator.addHeaderFile("Keypad.h");
		translator.addDefinitionCommand("const byte ROWS = 4; //four rows\n"
				+ "const byte COLS = 4; //three columns\n"
				+ "//define the cymbols on the buttons of the keypads\n"
				+ "char hexaKeys[ROWS][COLS] = {\n"
				+ "{ '1','2','3','A'},\n"
				+ "{'4','5','6','B'},\n"
				+ "{'7','8','9','C'},\n"
				+ "{'*','0','#','D'}\n"
				+ "};\n"
				+ "byte rowPins[ROWS] = {2,3,4,5}; //connect to the row pinouts of the keypad\n"
				+ "byte colPins[COLS] = {6,7,8,9}; //connect to the column pinouts of the keypad\n\n"
				+ "//initialize an instance of class NewKeypad\n"
				+ "Keypad customKeypad = Keypad( makeKeymap(hexaKeys), rowPins, colPins, ROWS, COLS); \n"	);
				
return "";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
