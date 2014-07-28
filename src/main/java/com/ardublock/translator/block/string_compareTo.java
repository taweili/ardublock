package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class string_compareTo extends TranslatorBlock
{
	public string_compareTo(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode()  throws SocketNullException, SubroutineNotDeclaredException
	{
		String first;
		String second;
		
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		first = translatorBlock.toCode();
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
		second = translatorBlock.toCode();
		String internalVariableName = translator.getNumberVariable(label);
		if (internalVariableName == null)
		{
			internalVariableName = translator.buildVariableName(label);
			translator.addNumberVariable(label, internalVariableName);
			translator.addDefinitionCommand("String " + internalVariableName + " = "+first +" ;");
//			translator.addSetupCommand(internalVariableName + " = \"\";");
		}
		String ret= internalVariableName + ".compareTo("+second+")< 0";
		return codePrefix + ret + codeSuffix;
	}

}
