package com.ardublock.translator.block.freesensors;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class FsDigitalOutput extends TranslatorBlock {

	public FsDigitalOutput(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException {
		String auxS;
		TranslatorBlock shield, port; 
		TranslatorBlock status;
		shield = this.getRequiredTranslatorBlockAtSocket(0);
		port = this.getRequiredTranslatorBlockAtSocket(1);
		status = this.getRequiredTranslatorBlockAtSocket(2);
		auxS = shield.toCode()+".pinMode("+"DIO"+port.toCode()+", OUTPUT);\n";
		translator.addSetupCommand(auxS);
		auxS = shield.toCode()+".digitalWrite("+"DIO"+port.toCode()+", "+status.toCode()+");\n";
		return auxS;
	}
}
