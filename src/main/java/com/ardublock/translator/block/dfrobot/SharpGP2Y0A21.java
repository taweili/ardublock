package com.ardublock.translator.block.dfrobot;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class SharpGP2Y0A21 extends TranslatorBlock {
	
	public SharpGP2Y0A21(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException {
		String func = "int ComputeSharpGP2Y0A21(int value) {\n";
		func +=  "if (value < 10) value = 10;\n";
	    func += "return ((67870.0 / (value - 3.0)) - 40.0);\n";
	    func += "}\n\n";
		translator.addDefinitionCommand(func);

		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		String ret = "ComputeSharpGP2Y0A21(analogRead(";
		ret = ret + translatorBlock.toCode();
		ret = ret + "))";
		
		return ret;
	}

}
