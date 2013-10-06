package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class SetterVariableVectorBlock extends TranslatorBlock
{
	public SetterVariableVectorBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		TranslatorBlock name = this.getRequiredTranslatorBlockAtSocket(0);
		TranslatorBlock position = this.getRequiredTranslatorBlockAtSocket(1);
		TranslatorBlock value = this.getRequiredTranslatorBlockAtSocket(2);
		String ret ="\t"+ name.toCode()+"["+position.toCode()+" - 1]";
		ret = ret + " = " + value.toCode() + " ;\n";
		return ret;
	}

}
