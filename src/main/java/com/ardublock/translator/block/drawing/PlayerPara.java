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

public class PlayerPara extends TranslatorBlock
{
		
	public PlayerPara(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String ret, pin, varName, actuatorParaDef;
		TranslatorBlock childBlock;	


		childBlock = getRequiredTranslatorBlockAtSocket(0);
		if (childBlock != null)
		{
			pin = childBlock.toCode();
		}
		else
		{
			pin = "UART";
		}

		varName = "aPara" + pin;

		varName = translator.buildVariableName(varName);
	
		actuatorParaDef = "byte " + varName + "[]={";

		childBlock = getTranslatorBlockAtSocket(1);
		if (childBlock != null)
		{
			actuatorParaDef += "PARATYPE_RUNMETHOD(" + childBlock.toCode() + "),";
		}

		childBlock = getTranslatorBlockAtSocket(2);
		if (childBlock != null)
		{
			actuatorParaDef += "PARATYPE_INDEX(" + childBlock.toCode() + ")";
		}

		actuatorParaDef += "};";

		
		translator.addDefinitionCommand(actuatorParaDef);

		/**/

		ret = "PALETTE_PIN_UART," + "FUNCTION_PLAYER_MINI," + "sizeof(" + varName +")," + varName;
		
		
		return ret;
	}
}
