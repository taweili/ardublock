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

public class SetParameters extends TranslatorBlock
{
	public SetParameters(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		Util.setupEnv(translator);

		String ret;
		String strVar;

		TranslatorBlock childBlock;

		ret = "DBG_BEGIN(9600);\n\n";

		childBlock = getTranslatorBlockAtSocket(0);

		if (childBlock != null)
		{
			ret += "Drawing.setWakeCondition(" + childBlock.toCode() + ");\n";
		}

		childBlock = getTranslatorBlockAtSocket(1);
		if (childBlock != null)
		{
			ret += "Drawing.setDuration(" + childBlock.toCode() + ");\n";
		}

		
		ret += "\n" + "PaletteRuleConfigRec_stru ruleTbl[]={";

		childBlock = getTranslatorBlockAtSocket(2);
		while (childBlock != null)
		{
			strVar = childBlock.toCode();

			if ( strVar.length() > 5 )
			{
				ret += "{" + strVar + "},";
			}
			
			childBlock = childBlock.nextTranslatorBlock();
		}
		
		ret += "};\n";

		ret += "\n";
		ret += "Drawing.init(ruleTbl, sizeof(ruleTbl)/sizeof(PaletteRuleConfigRec_stru));\n";
		
		return ret;
	}
}
