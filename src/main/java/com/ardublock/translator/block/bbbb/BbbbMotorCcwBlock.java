package com.ardublock.translator.block.bbbb;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class BbbbMotorCcwBlock extends  TranslatorBlock
{

	public BbbbMotorCcwBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		translator.addDefinitionCommand(BbbbBlock.BBBB_DEF);
		translator.addSetupCommand("lightOnIndicator(3);");
		translator.addSetupCommand("lightOnIndicator(4);");
		String speed = this.getRequiredTranslatorBlockAtSocket(0).toCode().trim();
		
		return String.format("bbbbMotor(0, %s);\n", speed);
	}

}
