package com.ardublock.translator.block;

import com.ardublock.translator.Translator;

public class VariableFakeBlock extends TranslatorBlock
{
	public VariableFakeBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode()
	{
		String internalVariableName = translator.getNumberVariable(label);
		internalVariableName = "vec_"+label.replace(" ","");
		return codePrefix+internalVariableName+codeSuffix;
	}

}
