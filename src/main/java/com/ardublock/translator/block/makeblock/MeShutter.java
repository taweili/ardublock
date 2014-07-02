package com.ardublock.translator.block.makeblock;
import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class MeShutter extends TranslatorBlock {

	public MeShutter(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException {
		translator.addHeaderFile("Makeblock.h");
		translator.addHeaderFile("SoftwareSerial.h");
		translator.addHeaderFile("Wire.h");
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		String ret = "MeShutter shutter"+translatorBlock.toCode()+"(PORT_"+translatorBlock.toCode()+");";
		translator.addDefinitionCommand(ret);
		TranslatorBlock state = this.getRequiredTranslatorBlockAtSocket(1);
		int stateId = Integer.parseInt(state.toCode());
		return "\tshutter"+translatorBlock.toCode()+(stateId==1?".shotOn()":(stateId==2?".shotOff()":(stateId==3?".focusOn()":".focusOff()")))+";\n";
	}

}
