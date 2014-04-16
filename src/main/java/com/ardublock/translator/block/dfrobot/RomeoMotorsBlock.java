// Not now used by standard blocks. Retained as long as it is referenced by legacy blocks.
package com.ardublock.translator.block.dfrobot;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.NumberBlock;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class RomeoMotorsBlock extends TranslatorBlock
{

	public static final String ROMEO_MOTOR_DEFINITION = 
			"void setRomeoMotor(int motorId, int speed)\n" + 
			"{\n" + 
			"  int speedPin, directionPin;\n" + 
			"  if (motorId == 1)\n" + 
			"  {\n" + 
			"    speedPin = 5;\n" + 
			"    directionPin = 4;\n" + 
			"  }\n" + 
			"  else\n" + 
			"  {\n" + 
			"    if (motorId == 2)\n" + 
			"    {\n" + 
			"      speedPin = 6;\n" + 
			"      directionPin = 7;\n" + 
			"    }\n" + 
			"    else\n" + 
			"    {\n" + 
			"      return;\n" + 
			"    }\n" + 
			"  }\n" + 
			"  \n" + 
			"  \n" + 
			"  if (speed == 0)\n" + 
			"  {\n" + 
			"    digitalWrite(speedPin, LOW);\n" + 
			"  }\n" + 
			"  if (speed > 0)\n" + 
			"  {\n" + 
			"    digitalWrite(directionPin, HIGH);\n" + 
			"    analogWrite(speedPin, speed);\n" + 
			"  }\n" + 
			"  else\n" + 
			"  {\n" + 
			"    digitalWrite(directionPin, LOW);\n" + 
			"    analogWrite(speedPin, -speed);\n" + 
			"  }\n" + 
			"}";
	
	public static void setupRomeoMotorPin(Translator translator)
	{
		translator.addOutputPin("4");
		translator.addOutputPin("5");
		translator.addOutputPin("6");
		translator.addOutputPin("7");
		
		translator.addSetupCommand("digitalWrite(4, LOW);\n");
		translator.addSetupCommand("digitalWrite(5, LOW);\n");
		translator.addSetupCommand("digitalWrite(6, LOW);\n");
		translator.addSetupCommand("digitalWrite(7, LOW);\n");
		
		translator.addDefinitionCommand(ROMEO_MOTOR_DEFINITION);
	}
	
	public RomeoMotorsBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		setupRomeoMotorPin(translator);
		
		String ret = "setRomeoMotor(1, ";
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		ret += translatorBlock.toCode();
		ret += ");\n";
		
		ret += "setRomeoMotor(2, ";
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
		ret += translatorBlock.toCode();
		ret += ");\n";

		return ret;
	}

}
