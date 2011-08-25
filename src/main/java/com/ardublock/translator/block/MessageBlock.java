package com.ardublock.translator.block;

import com.ardublock.translator.Translator;

public class MessageBlock extends TranslatorBlock
{
	protected MessageBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	public String toCode()
	{
		//TODO take out special character
		String ret;
		ret = label.replaceAll("\\\\", "\\\\\\\\");
		ret = ret.replaceAll("\"", "\\\\\"");
		ret = codePrefix + "\"" + ret + "\"" + codeSuffix;
		TranslatorBlock translatorBlock = this.getTranslatorBlockAtSocket(0, codePrefix, codeSuffix);
		if (translatorBlock != null)
		{
			ret = ret + translatorBlock.toCode();
		}
		return ret;
	}

}
