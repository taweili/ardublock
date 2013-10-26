package com.ardublock.translator.block.scoop;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class SCoopTaskBlock extends TranslatorBlock
{

	public SCoopTaskBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String ret;
		
		translator.addHeaderFile("SCoop.h");
		translator.addSetupCommand("mySCoop.start();");
		
		String taskName = SCoopTaskBlock.createScoopTaskName();
		ret = "defineTask(" + taskName + ")\n"
				+ "void " + taskName + "::setup()\n"
				+ "{\n";
		
		TranslatorBlock translatorBlock = getTranslatorBlockAtSocket(0);
		while (translatorBlock != null)
		{
			ret = ret + translatorBlock.toCode();
			translatorBlock = translatorBlock.nextTranslatorBlock();
		}
		ret = ret + "}\n\n"
				+ "void " + taskName + "::loop()\n"
				+ "{\n";
		
		translatorBlock = getTranslatorBlockAtSocket(1);
		while (translatorBlock != null)
		{
			ret = ret + translatorBlock.toCode();
			translatorBlock = translatorBlock.nextTranslatorBlock();
		}
		
		ret = ret + "}\n\n";
		
		return ret;

	}

	private static int taskId = 0;

	public static String createScoopTaskName()
	{
		++taskId;
		return "scoopTask" + taskId;
	}

}
