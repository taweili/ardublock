package com.ardublock.translator.block.freesensors;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class FsMotorServo extends TranslatorBlock {

	public FsMotorServo(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException {
		String auxS;
		TranslatorBlock shield, port; 
		TranslatorBlock angle;
		shield = this.getRequiredTranslatorBlockAtSocket(0);
		port = this.getRequiredTranslatorBlockAtSocket(1);
		angle = this.getRequiredTranslatorBlockAtSocket(2);
		auxS = shield.toCode()+".s"+port.toCode();
		translator.addSetupCommand(auxS+".attach();\n");
		auxS = auxS+".writeAngle("+angle.toCode()+");\n";
		return auxS;
	}
}
