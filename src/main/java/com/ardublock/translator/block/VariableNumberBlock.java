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
		String localVariableName = translator.getNumberLocalVariable(label);
		if (localVariableName == null)
		{
			localVariableName = translator.buildLocalVariableName(label);
			translator.addNumberVariable(label, localVariableName);
			//translator.addDefinitionCommand("int " + internalVariableName + ";");
			//translator.addSetupCommand("\t" + internalVariableName + " = 0;");
		}
		return codePrefix + localVariableName + codeSuffix;
	}

}
