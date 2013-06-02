package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;

public class SubroutineBlock extends TranslatorBlock
{

	public SubroutineBlock(Long blockId, Translator translator,
			String codePrefix, String codeSuffix, String label) {
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclatedException
	{
		String functionName = label.trim();
		translator
		// TODO Auto-generated method stub
		return null;
	}asdfasdf

	
	String internalVariableName = translator.getBooleanVariable(label);
	if (internalVariableName == null)
	{
		internalVariableName = translator.buildVariableName(label);
		translator.addBooleanVariable(label, internalVariableName);
		translator.addDefinitionCommand("bool " + internalVariableName + ";");
		translator.addSetupCommand(internalVariableName + " = false;");
	}
	//String ret = " ( " + internalVariableName + " ? true : false )";
	String ret = internalVariableName;
	return codePrefix + ret + codeSuffix;
}
