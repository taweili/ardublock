package com.ardublock.translator.block.freesensors;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class FsMotorDC extends TranslatorBlock {

	public FsMotorDC(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException {
		String auxS;
		TranslatorBlock shield, port; 
		TranslatorBlock action, speed;
		shield = this.getRequiredTranslatorBlockAtSocket(0);
		port = this.getRequiredTranslatorBlockAtSocket(1);
		action = this.getRequiredTranslatorBlockAtSocket(2);
		speed = this.getRequiredTranslatorBlockAtSocket(3);
		auxS = shield.toCode()+".m"+port.toCode();
		if (action.toCode().equals("forward")) auxS = auxS+".forward("+speed.toCode()+");\n";
		if (action.toCode().equals("backward")) auxS = auxS+".backward("+speed.toCode()+");\n";
		if (action.toCode().equals("stop")) auxS = auxS+".stop();\n";
		return auxS;
	}
}
