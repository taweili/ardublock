// Not now used by standard blocks. Retained as long as it is referenced by legacy blocks.
package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class ServoBlock extends TranslatorBlock
{

	public ServoBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		if (!(translatorBlock instanceof NumberBlock))
		{
			throw new BlockException(this.blockId, "the Pin# of Servo must a number");
		}
		
		
		NumberBlock pinNumberBlock = (NumberBlock)translatorBlock;
		String pinNumber = pinNumberBlock.toCode();
		String servoName = "servo_pin_" + pinNumber;
		
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
		
		String ret = servoName + ".write( " + translatorBlock.toCode() + " );\n";
		translator.addHeaderFile("Servo.h");
		translator.addDefinitionCommand("Servo " + servoName + ";");
		translator.addSetupCommand(servoName + ".attach(" + pinNumber + ");");
		return ret;
	}

}
