package com.ardublock.translator.block.u8x2display;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.NumberBlock;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class DrawNumberBlock extends TranslatorBlock
{
	public DrawNumberBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		String x = translatorBlock.toCode();
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
		String y = translatorBlock.toCode();
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(2);
		String num = translatorBlock.toCode();
		translator.addDefinitionCommand("char u8x8_number_buffer[30];");

		return "itoa(" + num + ",u8x8_number_buffer,10);\nu8x8." + getCommand() + "(" + x + "," + y + ",u8x8_number_buffer);\n";
	}

	protected String getCommand()
	{
		return "drawString";
	}
}
