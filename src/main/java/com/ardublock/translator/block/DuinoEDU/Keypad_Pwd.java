package com.ardublock.translator.block.Duinoedu;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Keypad_Pwd  extends TranslatorBlock {

	public Keypad_Pwd(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String Pwd;
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		Pwd = translatorBlock.toCode();

		translator.addSetupCommand("keypad.writePassword(\""+ Pwd +"\");");
		String ret = "keypad.testPasswordOk()";
		return codePrefix + ret + codeSuffix;
	}
}
