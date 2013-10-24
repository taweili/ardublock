package com.ardublock.translator.block.tinker;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;

public class TinkerOutputPortBlock extends TranslatorBlock
{

	public TinkerOutputPortBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
		super(blockId, translator, codePrefix, codeSuffix, label);
		translator.addHeaderFile("TinkerKit.h");
		//translator.addSetupCommand("pinMode( " + label + ", OUTPUT);");
	}

	@Override
	public String toCode() throws SocketNullException {
		return codePrefix + label + codeSuffix;
	}

}
