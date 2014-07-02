package com.ardublock.translator.block.drawing;

import java.util.Map;
import java.util.HashMap;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;



public class PinName extends TranslatorBlock
{
	
	private static Map<String, String> pinMap = new HashMap<String, String>();

	public PinName(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);

		if (pinMap.isEmpty())
		{
			pinMap.put("5", "5");
			pinMap.put("6", "6");
			pinMap.put("9", "9");
			pinMap.put("10", "10");
			pinMap.put("11", "11");
			pinMap.put("A0", "A0");
			pinMap.put("A1", "A1");
			pinMap.put("A2", "A2");
			pinMap.put("A3", "A3");
			pinMap.put("A4", "A4");
			pinMap.put("A5", "A5");
			pinMap.put("UART", "PALETTE_PIN_UART");
			pinMap.put("I2C", "PALETTE_PIN_I2C");
		}


	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String ret;


		ret = pinMap.get(label.trim().toUpperCase());
			
		if ( null == ret )
		{
			ret = "?";
		}
			
		return ret;
	}
	
}
