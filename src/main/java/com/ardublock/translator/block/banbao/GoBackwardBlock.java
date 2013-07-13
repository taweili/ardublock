package com.ardublock.translator.block.banbao;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class GoBackwardBlock extends MotorSetupBlock
{

	public GoBackwardBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException,
			SubroutineNotDeclaredException {
		// TODO Auto-generated method stub
		return "bbDrive(0, "+defaultSpeed+", 0, "+defaultSpeed+");\n" +
		"delay(200);\n";
	}
	
	@Override
	public void afterTranslation()
	{
		super.runningBlockDoAfterTranslator();
	}
}
