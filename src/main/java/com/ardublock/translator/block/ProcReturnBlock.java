package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import edu.mit.blocks.codeblocks.Block;

import com.ardublock.translator.block.exception.SocketNullException;

public class ProcReturnBlock extends TranslatorBlock
{
	protected ProcReturnBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	public String toCode() throws SocketNullException
	{

		String ret = "";
		TranslatorBlock tb = getRequiredTranslatorBlockAtSocket(0, "\treturn ", ";\n");
		ret = ret + tb.toCode();
		if (tb != null) {
			return ret;
			}
		else {
			return "";
		}
		
	}
}
