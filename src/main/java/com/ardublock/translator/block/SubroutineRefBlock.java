package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class SubroutineRefBlock extends TranslatorBlock
{

	public SubroutineRefBlock(Long blockId, Translator translator,
			String codePrefix, String codeSuffix, String label) {
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String subroutineName = label.trim();
		if (!translator.containFunctionName(subroutineName))
		{
			throw new SubroutineNotDeclaredException(blockId);
		}
		return "\t"+subroutineName + "();\n";
	}

}
