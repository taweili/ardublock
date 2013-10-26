package com.ardublock.translator.block.scoop;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class SCoopLoopBlock extends TranslatorBlock
{

	public SCoopLoopBlock(Long blockId, Translator translator,
			String codePrefix, String codeSuffix, String label) {
		super(blockId, translator, codePrefix, codeSuffix, label);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toCode() throws SocketNullException,
			SubroutineNotDeclaredException {
		// TODO Auto-generated method stub
		return "";
	}

}
