package com.ardublock.translator.block.freesensors;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class FsShieldSPIport extends TranslatorBlock {

	public FsShieldSPIport(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException {
		String auxS;
		TranslatorBlock name;
		name = this.getRequiredTranslatorBlockAtSocket(0);
		translator.addHeaderFile("FreeSensors.h");
		auxS = "FSspiport "+name.toCode()+";";
		translator.addDefinitionCommand(auxS);
		auxS = name.toCode()+".begin();";
		translator.addSetupCommand(auxS);
		return "";
	}
}
