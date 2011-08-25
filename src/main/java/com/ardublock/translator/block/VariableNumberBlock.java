package com.ardublock.translator.block;

import com.ardublock.translator.Translator;

public class VariableNumberBlock extends TranslatorBlock
{
	protected VariableNumberBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	public String toCode()
	{
		String internalVariableName = translator.getNumberVariable(label);
		if (internalVariableName == null)
		{
			internalVariableName = translator.buildVariableName();
			translator.addNumberVariable(label, internalVariableName);
			translator.addDefinitionCommand("int " + internalVariableName + ";");
			translator.addSetupCommand(internalVariableName + " = 0;");
		}
		return codePrefix + internalVariableName + codeSuffix;
	}

}
