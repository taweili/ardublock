package com.ardublock.translator.block.banbao;

import java.util.HashMap;
import java.util.Map;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class NumberBlock extends TranslatorBlock
{

	private static final String DEFAULT_EXTERNAL_PIN = "1";
	private static final String DEFAULT_INDICATOR_PIN = "0";
	private static final Map<String, String> pinMapExternal2Internal;
	private static final Map<String, String> portIndicatorMap;
	static
	{
		pinMapExternal2Internal = new HashMap<String, String>();
		pinMapExternal2Internal.put("1", "1");
		pinMapExternal2Internal.put("2", "2");
		pinMapExternal2Internal.put("3", "10");
		pinMapExternal2Internal.put("4", "11");
		pinMapExternal2Internal.put("5", "12");
		pinMapExternal2Internal.put("6", "13");
		pinMapExternal2Internal.put("7", "A0");
		pinMapExternal2Internal.put("8", "A1");
		pinMapExternal2Internal.put("9", "A2");
		pinMapExternal2Internal.put("10", "A5");
		
		portIndicatorMap = new HashMap<String, String>();
		portIndicatorMap.put("1", "0");
		portIndicatorMap.put("2", "3");
		portIndicatorMap.put("3", "4");
		portIndicatorMap.put("4", "5");
		portIndicatorMap.put("5", "6");
		portIndicatorMap.put("6", "7");
		portIndicatorMap.put("7", "8");
		portIndicatorMap.put("8", "9");
		portIndicatorMap.put("9", "A3");
		portIndicatorMap.put("10", "A4");
		
	}
	
	public NumberBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	public String getInternalPin(String externalPin)
	{
		String internalPin = pinMapExternal2Internal.get(externalPin);
		if (internalPin == null)
		{
			return DEFAULT_EXTERNAL_PIN;
		}
		else
		{
			return internalPin;
		}
	}
	
	
	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String externalPinNumber = label.replace("#", "").trim();
		String indicatorPin = portIndicatorMap.get(externalPinNumber);
		if (indicatorPin == null)
		{
			indicatorPin = DEFAULT_INDICATOR_PIN;
		}
		
		translator.addOutputPin(indicatorPin);
		translator.addSetupCommand("digitalWrite(" + indicatorPin + ", HIGH);");
		
		return getInternalPin(externalPinNumber);
	}

}
