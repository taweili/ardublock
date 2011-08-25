package com.ardublock.translator.block;

import com.ardublock.translator.Translator;

public class VariableDigitalBlock extends TranslatorBlock
{
	protected VariableDigitalBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	public String toCode()
	{
		String internalVariableName = translator.getBooleanVariable(label);
		if (internalVariableName == null)
		{
			internalVariableName = translator.buildVariableName();
			translator.addBooleanVariable(label, internalVariableName);
			translator.addDefinitionCommand("boolean " + internalVariableName + ";");
			translator.addSetupCommand(internalVariableName + " = false;");
		}
		return codePrefix + internalVariableName + codeSuffix;
	}

}
