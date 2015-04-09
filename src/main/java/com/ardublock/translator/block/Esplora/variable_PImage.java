package com.ardublock.translator.block.Esplora;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;

public class variable_PImage extends TranslatorBlock
{
	public variable_PImage(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode()
	{
		translator.addHeaderFile("SD.h");
		translator.addHeaderFile("TFT.h");
		
		String internalVariableName = translator.getNumberVariable(label);
		if (internalVariableName == null)
		{
			internalVariableName = translator.buildVariableName(label);
			translator.addNumberVariable(label, internalVariableName);
			translator.addDefinitionCommand("PImage " + internalVariableName + ";");
//			translator.addSetupCommand(internalVariableName + " = \"\";");
		}
		return codePrefix + internalVariableName + codeSuffix;
	}

}
