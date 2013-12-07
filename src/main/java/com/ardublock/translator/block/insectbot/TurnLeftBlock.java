package com.ardublock.translator.block.insectbot;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class TurnLeftBlock extends TranslatorBlock
{

	public TurnLeftBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		InsectBotUtil.setupEnv(translator);
		
		String ret = "insect.turnLeft();\n";
		
		return codePrefix + ret + codeSuffix;
	}

}
