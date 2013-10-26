package com.ardublock.translator.block.panda;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;

public class PandaOutputPortBlock extends TranslatorBlock
{

	/* pinTable represent the port mapping to read pins, first be the functional pin number and second being the LED pin */
	public int pinTable[][] = {
			{0, 0},
			{0, 1},
			{3, 2},
			{4, 10},
			{5, 11},
			{6, 12},
			{7, 13}
	};
			
	public PandaOutputPortBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException
	{
		String g_name = this.getTranslator().getBlock(blockId).getGenusName();
		String port_str = g_name.substring(7);
		int port = Integer.parseInt(port_str);
		int pin = pinTable[port][1];
		int led_pin = pinTable[port][0];
		translator.addSetupCommand("pinMode(" + pin + ", OUTPUT);");
		translator.addSetupCommand("pinMode(" + led_pin + ", OUTPUT);");
		translator.addSetupCommand("digitalWrite(" + led_pin + ", HIGH);");

		return pin + "";
	}
}