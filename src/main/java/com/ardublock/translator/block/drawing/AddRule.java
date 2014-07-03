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

public class AddRule extends TranslatorBlock
{
	public AddRule(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String ret = "";
		TranslatorBlock childBlock;	

		childBlock = getTranslatorBlockAtSocket(0);
		if (childBlock != null)
		{
			ret += childBlock.toCode() + ",";

			if ( label.equalsIgnoreCase("Sleep") )
			{
				ret += "ACTION_SYS_SLEEP";
			}
			else
			{

				if ( label.equalsIgnoreCase("Start") )
				{
					ret += "ACTION_RUN,";
				}
				else  //Stop
				{
					ret += "ACTION_STOP,";
				}
				
				childBlock = getTranslatorBlockAtSocket(1);
				if (childBlock != null)
				{
					ret += "{" + childBlock.toCode() + "}";
				}
				else
				{
					ret ="";
				}
				
			}


		}

		return ret;
	}
}
