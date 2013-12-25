package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;

public class SerialPrintBlock extends TranslatorBlock
{
	protected SerialPrintBlock(Long blockId, Translator translator)
	{
		super(blockId, translator);
	}

	@Override
	public String toCode() throws SocketNullException
	{
		translator.addSetupCommand("Serial.begin(9600);");
		int i=0;
		TranslatorBlock tb = getTranslatorBlockAtSocket(0, "\tSerial.print(", ");\n");
		//tb=this.nextTranslatorBlock();		
		String ret = "";
		while (tb != null)
		{
			ret = ret + tb.toCode();
			i=i+1;
			tb = getTranslatorBlockAtSocket(i, "\tSerial.print(", ");\n");
		}		
		//ret = ret.replace("\n", "\n\t");
		ret = ret + "\tSerial.print(\"\\t\");\n";
		
		return ret;
	}
}
