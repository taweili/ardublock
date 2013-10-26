package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class WatchdogBlock extends TranslatorBlock
{
	public WatchdogBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		setupWatchdog(translator);

		TranslatorBlock t5 = getRequiredTranslatorBlockAtSocket(0);
		String bol1 = t5.toString();
		
		if (bol1.startsWith("com.ardublock.translator.block.DigitalHigh"))
		{
			String ret = "wdt_enable( ";
			TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(1);
			ret = ret + tb.toCode();
			ret = ret + " );\n";

			return ret;
		}
		else
		{
			String ret = "wdt_disable();\n";

			return ret;
		}
	}

	public static void setupWatchdog(Translator t)
	{
		t.addHeaderFile("avr/wdt.h");
	}
}
