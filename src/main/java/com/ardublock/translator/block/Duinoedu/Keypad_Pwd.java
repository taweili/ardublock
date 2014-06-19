package com.ardublock.translator.block.DuinoEDU;

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

		translator.addDefinitionCommand("//libraries at http://www.duinoedu.com/\nkeypad.writePassword("+ Pwd +");");
		String ret = "testPassword()";
		return codePrefix + ret + codeSuffix;
	}
}
