package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;

public class SerialsendBlock extends TranslatorBlock
{
	protected SerialsendBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	public String toCode() throws SocketNullException
	{	
		String ret;
		TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);
		ret = tb.toCode();
		if (ret.startsWith("\"@")) ret = ret.substring(2,ret.length()-1);

		translator.addSetupCommand("\tSerial.begin(9600);");
		translator.addDefinitionCommand("void Serialsend(char *str)\n" +
		"{    \n" +
		"    while(*str){\n" +
		"        Serial.write(*str++);\n" +
		"    }\n" +
		"}\n");
			
		ret = codePrefix + "Serialsend(" + ret + ");\n";
		return ret;
	}
	
}
/*
SerialSend(String S)
{
int L= S.length();
S.getByte(B,L);
S.write(B,L);
}
*/
