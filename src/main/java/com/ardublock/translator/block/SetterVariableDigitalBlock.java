package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.BlockException;

public class SetterVariableDigitalBlock extends TranslatorBlock
{
	protected SetterVariableDigitalBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	public String toCode()
	{
		TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);
		if (!(tb instanceof VariableDigitalBlock))
		{
			throw new BlockException(blockId, "var must be var");
		}
		
		String ret = tb.toCode();
		tb = this.getRequiredTranslatorBlockAtSocket(1);
		ret = ret + " = " + tb.toCode() + " ;\n";
		return ret;
	}

}
