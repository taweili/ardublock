package com.ardublock.translator.block.Esplora;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;

public class Variable_File extends TranslatorBlock
{
	public Variable_File(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
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
			translator.addDefinitionCommand("File " + internalVariableName + ";");
//			translator.addSetupCommand(internalVariableName + " = \"\";");
		}
		return codePrefix + internalVariableName + codeSuffix;
	}

}
