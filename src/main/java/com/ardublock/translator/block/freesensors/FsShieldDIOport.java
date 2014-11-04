package com.ardublock.translator.block.freesensors;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class FsShieldDIOport extends TranslatorBlock {

	public FsShieldDIOport(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException {
		String auxS;
		TranslatorBlock name,address;
		name = this.getRequiredTranslatorBlockAtSocket(0);
		address = this.getRequiredTranslatorBlockAtSocket(1);
		translator.addHeaderFile("FreeSensors.h");
		auxS = "FSdioport "+name.toCode()+";";
		translator.addDefinitionCommand(auxS);
		auxS = name.toCode()+".begin("+"PCF_"+address.toCode()+");";
		translator.addSetupCommand(auxS);
		return "";
	}
}
