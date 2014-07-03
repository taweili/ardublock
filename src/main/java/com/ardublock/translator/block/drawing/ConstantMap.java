package com.ardublock.translator.block.drawing;

import java.util.Map;
import java.util.HashMap;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;



public class ConstantMap extends TranslatorBlock
{
	
	private static Map<String, String> map = new HashMap<String, String>();

	public ConstantMap(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);

		if (map.isEmpty())
		{
			map.put("flash", "LED_LIGHTMETHOD_FLASH");
			map.put("fade", "LED_LIGHTMETHOD_FADE");
			map.put("random", "RUNMETHOD_RANDOM");
			map.put("single", "RUNMETHOD_FILEINDEX");
			map.put("==", "RELATION_EQUAL");
			map.put(">", "RELATION_MORE");
			map.put("<", "RELATION_LESS");
			map.put("high", "HIGH");
			map.put("low", "LOW");
			map.put("high pulse", "RUNMETHOD_PULSE_POSITIVE");
			map.put("low pulse", "RUNMETHOD_PULSE_NEGATIVE");

		}


	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String ret;


		ret = map.get(label.trim().toLowerCase());
			
		if ( null == ret )
		{
			ret = "?";
		}
			
		return ret;
	}
	
}
