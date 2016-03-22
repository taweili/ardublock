package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class SubroutineRefBlock_var extends TranslatorBlock
{

	public SubroutineRefBlock_var(Long blockId, Translator translator,
			String codePrefix, String codeSuffix, String label) {
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String var;
		String subroutineName = label.trim();
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		var = translatorBlock.toCode();
		if (!translator.containFunctionName(subroutineName))
		{
			throw new SubroutineNotDeclaredException(blockId);
		}
		return "\t"+subroutineName + "("+var+");\n";
	}

}
