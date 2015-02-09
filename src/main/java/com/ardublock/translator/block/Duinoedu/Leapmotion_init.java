package com.ardublock.translator.block.Duinoedu;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Leapmotion_init extends TranslatorBlock {
	public Leapmotion_init(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		translator.addHeaderFile("Wire.h");
		translator.addHeaderFile("avr/pgmspace.h");
		translator.addDefinitionCommand("String inputString = \"\"; \n String commandString = \"\"; \nint hand_positionX=0;\nint hand_positionY=0;\nint hand_positionZ=0;\nint handRotX=0;\nint handRotY=0;\nint handRotZ=0;\nint angle_pouce=0;\nint angle_index=0;\nint angle_majeur=0;\nint angle_annulaire=0;\nint angle_auriculaire=0;\nboolean stringComplete = false; \n");
		translator.addDefinitionCommand(leapmotionFunctions);
		translator.addSetupCommand(" Serial.begin(9600);");		
		
		String ret =  "if (stringComplete) {\n"+"    inputString.trim();\n    int index = inputString.indexOf(\"leapmotion\");\n    if(index!=0){\n      return;\n    }\n     int pos0 =inputString.indexOf(\':\');\n     inputString=inputString.substring(pos0+1);\n     pos0 = inputString.indexOf(':');\n     hand_positionX=inputString.substring(0,pos0).toInt();\n     inputString=inputString.substring(pos0+1);\n     pos0 = inputString.indexOf(':');\n     hand_positionY=inputString.substring(0,pos0).toInt();\n     inputString=inputString.substring(pos0+1);\n     pos0 = inputString.indexOf(':');\n     hand_positionZ=inputString.substring(0,pos0).toInt();\n     inputString=inputString.substring(pos0+1);\n     pos0 = inputString.indexOf(':');\n     handRotX=inputString.substring(0,pos0).toInt();\n     inputString=inputString.substring(pos0+1);\n     pos0 = inputString.indexOf(':');\n     handRotY=inputString.substring(0,pos0).toInt();\n     inputString=inputString.substring(pos0+1);\n     pos0 = inputString.indexOf(':');\n     handRotZ=inputString.substring(0,pos0).toInt();\n     inputString=inputString.substring(pos0+1);\n     pos0 = inputString.indexOf(':');\n     angle_pouce=inputString.substring(0,pos0).toInt();\n     inputString=inputString.substring(pos0+1);\n     pos0 = inputString.indexOf(':');\n     angle_index=inputString.substring(0,pos0).toInt();\n     inputString=inputString.substring(pos0+1);\n     pos0 = inputString.indexOf(':');\n     angle_majeur=inputString.substring(0,pos0).toInt();\n     inputString=inputString.substring(pos0+1);\n     pos0 = inputString.indexOf(':');\n     angle_annulaire=inputString.substring(0,pos0).toInt();\n     inputString=inputString.substring(pos0+1);\n     pos0 = inputString.indexOf(':');\n     angle_auriculaire=inputString.substring(0,pos0).toInt();\n     inputString = \"\";\n     commandString = \"\";\n     stringComplete = false;\n";
		
		return  ret ;
	}
	
	private static final String leapmotionFunctions = 
	"		void serialEvent() {\n"+
	"	  while (Serial.available()) {\n"+
	"	    // get the new byte:\n"+
	"	    char inChar = (char)Serial.read(); \n"+
	"	    \n"+
	"	    // add it to the inputString:\n"+
	"	    inputString += inChar;\n"+
	"	    \n"+
	"	    // if the incoming character is a newline, set a flag\n"+
	"	    // so the main loop can do something about it:\n"+
	"	    if (inChar == '\\n') {\n"+
	"	      stringComplete = true;\n"+
	"	      \n"+
	"	      //return;\n"+
	"	    } \n"+
	"	  }\n"+
	"	}\n";
	
	
	
	
	
	
}
