package com.ardublock.translator.block.adafruit;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
//import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;
	
public class step_fwd extends TranslatorBlock
{

	public step_fwd(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
				
		TranslatorBlock ChannelBlock = this.getRequiredTranslatorBlockAtSocket(0);
		TranslatorBlock Steps_nbBlock = this.getRequiredTranslatorBlockAtSocket(1);
		TranslatorBlock RpmBlock = this.getRequiredTranslatorBlockAtSocket(2);
		TranslatorBlock StepsBlock = this.getRequiredTranslatorBlockAtSocket(3);
		String ChannelNumber = ChannelBlock.toCode();
		String Steps_nb = Steps_nbBlock.toCode();
		String rpm = RpmBlock.toCode();
		String Steps = StepsBlock.toCode();
		
		if (Integer.parseInt(ChannelNumber) > 2 ) 
		{
			throw new BlockException(this.blockId, "the Channel# of Stepper Motor must be 1 or 2");
			//ChannelNumber = "2";
		}
		
		
		String MotorName = "stepper_motor" + ChannelNumber;
		
		String ret = "\t" + MotorName + ".step(" + Steps + ", FORWARD, DOUBLE);\n";
		translator.addHeaderFile("AFMotor.h");
		translator.addSetupCommand(MotorName + ".setSpeed(" + rpm + ");");
		translator.addDefinitionCommand("AF_Stepper " + MotorName + "(" + Steps_nb + ", " + ChannelNumber + ");");

		return ret;
		}

	}
