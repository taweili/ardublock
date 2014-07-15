package com.ardublock.translator.block.keenlon;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.NumberBlock;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class TFTPrintAddParamBlock extends TranslatorBlock
{
	public TFTPrintAddParamBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		//TODO take out special character
		String ret;
		ret = " , ";
		String internalVariableName = translator.getNumberVariable(label);
		if (internalVariableName == null)
		{
			internalVariableName = translator.buildVariableName(label);
			translator.addNumberVariable(label, internalVariableName);
			translator.addDefinitionCommand("int " + internalVariableName + ";");
			translator.addSetupCommand(internalVariableName + " = 0;");
		}
		ret = ret + internalVariableName;
		
		TranslatorBlock translatorBlock = this.getTranslatorBlockAtSocket(0, codePrefix, codeSuffix);
		if (translatorBlock != null)
		{
			ret = ret + translatorBlock.toCode();
		}

		return ret;
		
	}
	

}
