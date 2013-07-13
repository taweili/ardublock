package com.ardublock.translator.block.banbao;

import java.util.ResourceBundle;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class MotorSetupBlock extends TranslatorBlock
{

	public MotorSetupBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	protected ResourceBundle messageResourceBundle = ResourceBundle.getBundle("com/ardublock/block/banbao/message");
	
	private static String bbDriveFunction =
"void bbDrive(int leftForwardSpeed, int leftBackwardSpeed, int rightForwardSpeed, int rightBackwardSpeed)\n" + 
"{\n" +
"	leftForwardSpeed = constrain(leftForwardSpeed, 0, 255);\n" + 
"	leftBackwardSpeed = constrain(leftBackwardSpeed, 0, 255);\n" + 
"	rightForwardSpeed = constrain(rightForwardSpeed, 0, 255);\n" + 
"	rightBackwardSpeed = constrain(rightBackwardSpeed, 0, 255);\n" + 
"	\n" + 
"	if (leftForwardSpeed > 0)\n" + 
"	{\n" + 
"		leftBackwardSpeed = 0;\n" + 
"	}\n" + 
"	\n" + 
"	if (rightForwardSpeed > 0)\n" + 
"	{\n" + 
"		rightBackwardSpeed = 0;\n" + 
"	}\n" + 
"	\n" + 
"	if (leftForwardSpeed > 0)\n" + 
"	{\n" + 
"		analogWrite(bbLeftForwardPin, leftForwardSpeed);\n" +
"	}\n" + 
"	else\n" + 
"	{\n" + 
"		digitalWrite(bbLeftForwardPin, LOW);\n" + 
"	}\n" + 
"	\n" + 
"	if (leftBackwardSpeed > 0)\n" + 
"	{\n" + 
"		analogWrite(bbLeftBackwordPin, leftBackwardSpeed);\n" + 
"	}\n" + 
"	else\n" + 
"	{\n" + 
"		digitalWrite(bbLeftBackwordPin, LOW);\n" + 
"	}\n" + 
"	\n" + 
"	if (rightForwardSpeed > 0)\n" + 
"	{\n" + 
"		analogWrite(bbRightForwardPin, rightForwardSpeed);\n" + 
"	}\n" + 
"	else\n" + 
"	{\n" + 
"		digitalWrite(bbRightForwardPin, LOW);\n" + 
"	}\n" + 
"	\n" + 
"	if (rightBackwardSpeed > 0)\n" + 
"	{\n" + 
"		analogWrite(bbRightBackwardPin, rightBackwardSpeed);\n" + 
"	}\n" + 
"	else\n" + 
"	{\n" + 
"		digitalWrite(bbRightBackwardPin, LOW);\n" + 
"	}\n" + 
"}\n";

	
	private static final String VARIABLE_DEFINITION =
"int bbLeftForwardPin = {bbLeftForwardPin};\n" + 
"int bbLeftBackwordPin = {bbLeftBackwordPin};\n" + 
"int bbRightForwardPin = {bbRightForwardPin};\n" + 
"int bbRightBackwardPin = {bbRightBackwardPin};";
	
	@Override
	public String toCode() throws SocketNullException,
			SubroutineNotDeclaredException
	{
		
		if (translator.getInternalVar("isBanBaoMotorSetupPut") == null)
		{
			translator.setInternalVar("isBanBaoMotorSetupPut", "true");
		}
		else
		{
			return "";
		}
		TranslatorBlock translatorBlock = getRequiredTranslatorBlockAtSocket(0);
		String bbLeftForwardPin = translatorBlock.toCode();
		translatorBlock = getRequiredTranslatorBlockAtSocket(1);
		String bbLeftBackwordPin = translatorBlock.toCode();
		translatorBlock = getRequiredTranslatorBlockAtSocket(2);
		String bbRightForwardPin = translatorBlock.toCode();
		translatorBlock = getRequiredTranslatorBlockAtSocket(3);
		String bbRightBackwardPin = translatorBlock.toCode();
		
		
		String definitionCommand = VARIABLE_DEFINITION.replaceAll("\\{bbLeftForwardPin}", bbLeftForwardPin);
		definitionCommand = definitionCommand.replaceAll("\\{bbLeftBackwordPin}", bbLeftBackwordPin);
		definitionCommand = definitionCommand.replaceAll("\\{bbRightForwardPin}", bbRightForwardPin);
		definitionCommand = definitionCommand.replaceAll("\\{bbRightBackwardPin}", bbRightBackwardPin);
		
		translator.addDefinitionCommand(definitionCommand);
		
		String setupCommand = "pinMode(" + bbLeftForwardPin + ", OUTPUT);";
		translator.addSetupCommand(setupCommand);
		setupCommand = "pinMode(" + bbLeftBackwordPin + ", OUTPUT);";
		translator.addSetupCommand(setupCommand);
		setupCommand = "pinMode(" + bbRightForwardPin + ", OUTPUT);";
		translator.addSetupCommand(setupCommand);
		setupCommand = "pinMode(" + bbRightBackwardPin + ", OUTPUT);";
		translator.addSetupCommand(setupCommand);
		
		translator.addDefinitionCommand(bbDriveFunction);
		
		return "";
	}

	protected void runningBlockDoAfterTranslator()
	{
		if (this.translator.getInternalVar("isBanBaoMotorSetupPut") == null)
		{
			throw new BlockException(blockId, messageResourceBundle.getString("motorSetupBlockMissing"));
		}
	}
	
	protected int defaultSpeed = 100;
}
