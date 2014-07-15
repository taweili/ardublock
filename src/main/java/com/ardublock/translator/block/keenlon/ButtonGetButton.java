package com.ardublock.translator.block.keenlon;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.NumberBlock;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class ButtonGetButton extends TranslatorBlock
{
	public ButtonGetButton(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		translator.addHeaderFile("keenlon.h");		
		translator.addDefinitionCommand("Button " + "btn" + ";");
		String ret = "btn.getButton()";

		return codePrefix + ret + codeSuffix;

	}	
}

