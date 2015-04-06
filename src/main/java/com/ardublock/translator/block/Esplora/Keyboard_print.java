package com.ardublock.translator.block.Esplora;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Keyboard_print extends TranslatorBlock
{
	public Keyboard_print(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		/**
		 * DO NOT add tab in code any more, we'll use arduino to format code, or the code will duplicated. 
		 */
		translator.addSetupCommand("Keyboard.begin();");
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0, "Keyboard.print(", ");\nKeyboard.print(\" \");\n");
		
		String ret = translatorBlock.toCode();
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
		String test=translatorBlock.toCode();
//		ret+=test;
		if(test.equals("true")){
		    ret+="Keyboard.println();\n";
		}
		return ret;
	}
}
