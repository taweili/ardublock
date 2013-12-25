package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;

public class GlobalVarBooleanBlock extends TranslatorBlock
{
	protected GlobalVarBooleanBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	public String toCode() throws SocketNullException
	{
/*		TranslatorBlock tb = this.getTranslatorBlockAtSocket(0);
		String internalVariableName = translator.getBooleanVariable(label);
		if (internalVariableName == null)
		{
			internalVariableName = translator.buildVariableName(codePrefix);
			translator.addBooleanVariable(codePrefix, internalVariableName);
			if (tb != null) {
				translator.addDefinitionCommand("boolean " + internalVariableName + " = " + tb.toCode() + ";");
			}
			else {
				translator.addDefinitionCommand("boolean " + internalVariableName + ";");
			}
		}*/
		return null;

	}

}
