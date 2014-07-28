package com.ardublock.translator.block;

import com.ardublock.translator.Translator;

public class variable_String extends TranslatorBlock
{
	public variable_String(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
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
			translator.addDefinitionCommand("String " + internalVariableName + " = \"\" ;");
//			translator.addSetupCommand(internalVariableName + " = \"\";");
		}
		return codePrefix + internalVariableName + codeSuffix;
	}

}
