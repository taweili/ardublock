package com.ardublock.translator.block;

import com.ardublock.translator.Translator;

public class WireIsReadBlock extends TranslatorBlock
{
	public WireIsReadBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode()
	{
		WireReadBlock.setupWireEnvironment(translator);
		return codePrefix + " __ardublockIsI2cReadOk " + codeSuffix;
	}

}
