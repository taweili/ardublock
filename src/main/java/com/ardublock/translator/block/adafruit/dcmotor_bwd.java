package com.ardublock.translator.block.adafruit;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
//import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;
	
public class dcmotor_bwd extends TranslatorBlock
{

	public dcmotor_bwd(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
			
			
		TranslatorBlock ChannelBlock = this.getRequiredTranslatorBlockAtSocket(0);
		TranslatorBlock SpeedBlock = this.getRequiredTranslatorBlockAtSocket(1);
		String ChannelNumber = ChannelBlock.toCode();
		String Speed = SpeedBlock.toCode();
		String MotorDeclare;
		
		if (Integer.parseInt(ChannelNumber) > 4 ) 
		{
			throw new BlockException(this.blockId, "the Channel# of DC Motor must be 1,2,3 or 4");
			//ChannelNumber = "4";
		}
		if (Integer.parseInt(Speed) > 255 ) 
		{
			throw new BlockException(this.blockId, "the Speed of DC Motor must be 0(stop) to 255(full speed)");
			//Speed = "255";
		}
		
		if (Integer.parseInt(ChannelNumber) == 1 || Integer.parseInt(ChannelNumber) == 2)
		{
			MotorDeclare = "MOTOR12_64KHZ";		
		}
		else
		{
			MotorDeclare = "MOTOR34_64KHZ";
		}
		
		String MotorName = "motor_dc_" + ChannelNumber;
			
		String ret = "\t" + MotorName + ".setSpeed(" + Speed + ");\n";
		ret += "\t" + MotorName + ".run(BACKWARD);\n";
		translator.addHeaderFile("AFMotor.h");
		translator.addDefinitionCommand("AF_DCMotor " + MotorName + "("+ ChannelNumber + ", " + MotorDeclare + ");");
		//translator.addSetupCommand(MotorName + ".setSpeed(" + Speed + ");");

		return ret;
		}

	}
