package com.ardublock.translator.block.scoop;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class SCoopTaskBlock extends TranslatorBlock
{

	public SCoopTaskBlock(Long blockId, Translator translator,
			String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException,
			SubroutineNotDeclaredException
	{
		// TODO Auto-generated method stub
		return "";
	}
	
	private static int taskId = 0;

	public static String createScoopTaskName()
	{
		++taskId;
		return "scoopTask" + taskId;
	}

}

