package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;

public class ForBlock extends TranslatorBlock
{
	
	protected ForBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	public String toCode() throws SocketNullException
	{
		TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);
		if (!(tb instanceof VariableNumberBlock))
		{
			throw new BlockException(blockId, "digital var must be digital var");
		}
				
		String varName = tb.toCode();		
		translator.addDefinitionCommand("int " + varName + ";");
		tb = this.getRequiredTranslatorBlockAtSocket(1);
		String ret = "\tfor (" + varName + "=" + tb.toCode() + "; " + varName + "< ";
		tb = this.getRequiredTranslatorBlockAtSocket(2);
		ret = ret + tb.toCode() + "; " + varName + "=" + varName + "+";
		tb = this.getRequiredTranslatorBlockAtSocket(3);
		ret = ret + tb.toCode() + ") {\n";
		
		
		tb = getTranslatorBlockAtSocket(4);
		while (tb != null)
		{
			ret = ret + tb.toCode();
			tb = tb.nextTranslatorBlock();
		}
		
		ret = ret.replace("\n", "\n\t");
		ret = ret + "}\n";
		return ret;
	}

}
	