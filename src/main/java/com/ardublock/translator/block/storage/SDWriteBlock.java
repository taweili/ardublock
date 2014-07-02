package com.ardublock.translator.block.storage;

import com.ardublock.core.Context;
import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class SDWriteBlock extends TranslatorBlock
{
	public SDWriteBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		SDWriteNumberBlock.setupSDEnvironment(translator);
		
		String ret = "";
		
		Context context = Context.getContext();
		if (context.getArduinoVersionString().equals(Context.ARDUINO_VERSION_UNKNOWN))
		{
			ret += "//Unable to detect your Arduino version, using 1.0 in default\n";
		}
		
		TranslatorBlock t1 = getRequiredTranslatorBlockAtSocket(0);
		String b1 = t1.toCode();
		TranslatorBlock t2 = getRequiredTranslatorBlockAtSocket(1);
		String b2 = t2.toCode();
		TranslatorBlock t3 = getRequiredTranslatorBlockAtSocket(2);
		String b3 = t3.toCode();
		//Switch was not used for compatibility with java 1.6

		if (b2.equals("Return")) {

			ret += "__ardublockWriteStringSDln ( ";
			
        } else {

        	ret += "__ardublockWriteStringSD ( ";

		}		
			ret = ret + b1;
			ret = ret +",";
			ret = ret + b3;
			ret = ret + ");\n";

		return codePrefix + ret + codeSuffix;
	}
	


	
}
