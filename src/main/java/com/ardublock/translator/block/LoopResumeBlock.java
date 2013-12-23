package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;

public class LoopResumeBlock extends TranslatorBlock
{

	protected LoopResumeBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	public String toCode() throws SocketNullException
	{
		String ret = "\tresume( ";
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		if (translatorBlock != null) {
			String name = translatorBlock.toCode().toString();
			if (name.equals("All")) {
				ret = "\tresumeAll( ";
			}
			else {
				ret = "\tresumeTask( " + name;
				ret = ret.replaceAll("\"", "");
			}
		}
		ret = ret + " );\n";
		return ret;
	}

}
