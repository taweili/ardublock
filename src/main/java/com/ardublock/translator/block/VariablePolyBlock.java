package com.ardublock.translator.block;

import com.ardublock.translator.Translator;

public class VariablePolyBlock extends TranslatorBlock
{
	public VariablePolyBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode()
	{
		String internalVariableName = translator.getNumberVariable(label);
		if (internalVariableName == null)
		{
			internalVariableName = translator.buildVariableName(label);
			translator.addNumberVariable(label, internalVariableName);
			translator.addDefinitionCommand("char " + internalVariableName + " = \' \' ;");
//			translator.addSetupCommand(internalVariableName + " = 0;");
		}
		return codePrefix + internalVariableName + codeSuffix;
	}

}
