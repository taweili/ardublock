package com.ardublock.translator.block.Duinoedu;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Servo_Plus  extends TranslatorBlock {

	public Servo_Plus (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	//@Override
		public String toCode() throws SocketNullException, SubroutineNotDeclaredException
		{
			String Pin;
			String Angle;
			String Speed;
			TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
			Pin = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
			Angle = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(2);
			Speed = translatorBlock.toCode();
			
			translator.addHeaderFile("Servo.h");
			translator.addHeaderFile("ServoPlus.h");
			translator.addDefinitionCommand("//libraries at http://duinoedu.com/dl/lib/grove/EDU_ServoPlus_GroveDupont/ \n"
					+ "ServoPlus monServoPlus"+Pin+";");
			translator.addSetupCommand("monServoPlus"+Pin+".brancher("+Pin+");\n");
			
			return codePrefix +"monServoPlus"+Pin+".ecrireAngle("+Angle+","+Speed+");"+ codeSuffix;
		}
}
