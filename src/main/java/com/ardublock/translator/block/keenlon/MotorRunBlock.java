
package com.ardublock.translator.block.keenlon;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.NumberBlock;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class MotorRunBlock extends TranslatorBlock
{

	public MotorRunBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		
		translator.addHeaderFile("keenlon.h");		
		translator.addDefinitionCommand("Motor motor;");
		translator.addSetupCommand("motor.init();");
		
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		String leftspeed = translatorBlock.toCode();
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
		String rightspeed = translatorBlock.toCode();
		
		String ret = "motor.run(" + leftspeed + " , " + rightspeed + ");\n";
		return  ret;		
		
		
		
	}

}



