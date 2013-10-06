package com.ardublock.translator.block.xinchejian;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class XinchejianMotorForwardBlock extends TranslatorBlock
{
	public static final String ARDUBLOCK_MOTOR_DEFINITION = "#define XINCHEJIAN_MOTORSHIELD_M1_FORWARD	5\n#define XINCHEJIAN_MOTORSHIELD_M1_BACKWARD	6\n#define XINCHEJIAN_MOTORSHIELD_M2_FORWARD	9\n#define XINCHEJIAN_MOTORSHIELD_M2_BACKWARD	10\n\nvoid __ardublock_xinchejian_ms(int motorNumber, boolean forward, int speed)\n{\nif (speed > 255)\n{\nspeed = 255;\n}\nif (speed < 0)\n{\nspeed = 0;\n}\nif (motorNumber == 1)\n{\nif (forward)\n{\ndigitalWrite(XINCHEJIAN_MOTORSHIELD_M1_BACKWARD, LOW);\nanalogWrite(XINCHEJIAN_MOTORSHIELD_M1_FORWARD, speed);\n}\nelse\n{\ndigitalWrite(XINCHEJIAN_MOTORSHIELD_M1_FORWARD, LOW);\nanalogWrite(XINCHEJIAN_MOTORSHIELD_M1_BACKWARD, speed);\n}\n}\nelse\n{\nif (motorNumber == 2)\n{\nif (forward)\n{\ndigitalWrite(XINCHEJIAN_MOTORSHIELD_M2_BACKWARD, LOW);\nanalogWrite(XINCHEJIAN_MOTORSHIELD_M2_FORWARD, speed);\n}\nelse\n{\ndigitalWrite(XINCHEJIAN_MOTORSHIELD_M2_FORWARD, LOW);\nanalogWrite(XINCHEJIAN_MOTORSHIELD_M2_BACKWARD, speed);\n}\n}\n}\n}";
	
	public XinchejianMotorForwardBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		translator.addDefinitionCommand(ARDUBLOCK_MOTOR_DEFINITION);
		
		String ret = "__ardublock_xinchejian_ms( ";
		TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);
		ret = ret + tb.toCode();
		ret = ret + " , true, ";
		tb = this.getRequiredTranslatorBlockAtSocket(1);
		ret = ret + tb.toCode();
		ret = ret + " );\n";
		
		return ret;
	}

	
//CPP code
/*

#define XINCHEJIAN_MOTORSHIELD_M1_FORWARD	5
#define XINCHEJIAN_MOTORSHIELD_M1_BACKWARD	6
#define XINCHEJIAN_MOTORSHIELD_M2_FORWARD	9
#define XINCHEJIAN_MOTORSHIELD_M2_BACKWARD	10


void __ardublock_xinchejian_ms(int motorNumber, boolean forward, int speed)
{
	if (speed > 255)
	{
		speed = 255;
	}
	if (speed < 0)
	{
		speed = 0;
	}
	if (motorNumber == 1)
	{
		if (forward)
		{
			digitalWrite(XINCHEJIAN_MOTORSHIELD_M1_BACKWARD, LOW);
			analogWrite(XINCHEJIAN_MOTORSHIELD_M1_FORWARD, speed);
		}
		else
		{
			digitalWrite(XINCHEJIAN_MOTORSHIELD_M1_FORWARD, LOW);
			analogWrite(XINCHEJIAN_MOTORSHIELD_M1_BACKWARD, speed);
		}
	}
	else
	{
		if (motorNumber == 2)
		{
			if (forward)
			{
				digitalWrite(XINCHEJIAN_MOTORSHIELD_M2_BACKWARD, LOW);
				analogWrite(XINCHEJIAN_MOTORSHIELD_M2_FORWARD, speed);
			}
			else
			{
				digitalWrite(XINCHEJIAN_MOTORSHIELD_M2_FORWARD, LOW);
				analogWrite(XINCHEJIAN_MOTORSHIELD_M2_BACKWARD, speed);
			}
		}
	}
}
*/
}
