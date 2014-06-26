/**************************************************
Description   : 
Author	  : Leo Yan
Created 	  : 2014/6
**************************************************/

package com.ardublock.translator.block.drawing;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class TimePara extends TranslatorBlock
{
	public TimePara(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String ret;
		TranslatorBlock childBlock;	

		ret = "{PALETTE_PIN_VIRTUAL_TIME, FUNCTION_SENSOR_VIRTUALTIME },";


		childBlock = getTranslatorBlockAtSocket(0);
		if (childBlock != null)
		{
			ret += childBlock.toCode() + ",";
		}
		else
		{
			ret += "0,";
		}
		
		ret += "RELATION_EQUAL";
		
		return ret;
	}
}
