package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;

public class SwitchBlock extends TranslatorBlock
{
	
	protected SwitchBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	public String toCode() throws SocketNullException
	{
		TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0, "\tswitch (", ") {\n");
		if (!(tb instanceof VariableNumberBlock))
		{
			throw new BlockException(blockId, "Value must be a number variable");
		}
		
		String ret = tb.toCode();
		tb = this.getRequiredTranslatorBlockAtSocket(1, "case ",":\n");
		int i = 1;
		while (tb != null)
		{
			while (tb != null)
			{
				ret = ret + tb.toCode();
				tb = tb.nextTranslatorBlock();
			}
			i=i+1;		
			tb = getTranslatorBlockAtSocket(i, "\tbreak;\ncase ",":\n");
		}
		
		
		tb = getTranslatorBlockAtSocket(i+2);
		if (tb != null) {
			ret = ret + "\tbreak;\ndefault:\n";
		}
		while (tb != null)
		{
			ret = ret + tb.toCode();
			tb = tb.nextTranslatorBlock();
		}
	
		ret = ret.replace("\n", "\n\t");
		ret = ret + "}\n";
		return ret;
	}
/*	public String toCode() throws SocketNullException
	{
		TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0, "switch (", ") {\n");
		if (!(tb instanceof VariableNumberBlock))
		{
			throw new BlockException(blockId, "Value must be a number variable");
		}
		
		String ret = tb.toCode();
		tb = this.getRequiredTranslatorBlockAtSocket(1, "case ",":\n");
		int i = 1;
		while (tb != null)
		{
			while (tb != null)
			{
				ret = ret + "\t" + tb.toCode() + "\t";
				tb = tb.nextTranslatorBlock();
			}
			i=i+1;		
			tb = getTranslatorBlockAtSocket(i, "break;\n\tcase ",":\n");
		}
		
		
		tb = getTranslatorBlockAtSocket(i+2);
		if (tb != null) {
			ret = ret + "\tbreak;\n\tdefault:\n";
		}
		while (tb != null)
		{
			ret = ret + "\t\t" + tb.toCode();
			tb = tb.nextTranslatorBlock();
		}
	
		ret = ret + "}\n\n";
		return ret;
	}*/

}
	