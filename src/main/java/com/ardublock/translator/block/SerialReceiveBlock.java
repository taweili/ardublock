package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;

public class SerialReceiveBlock extends TranslatorBlock
{
	protected SerialReceiveBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	public String toCode() throws SocketNullException
	{
		String ret;
		TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);
		ret = tb.toCode();
		ret = "char buffer[" + tb.toCode() + "];\n\n";
		translator.addDefinitionCommand(ret);
		translator.addSetupCommand("\tSerial.begin(9600);");
		//translator.addDefinitionCommand("char buffer[128];\n\n" +
		translator.addDefinitionCommand("char *Serialreceive(char *str)\n" +
		"{    " +
		"    char c;\n" +
		"    do{\n" +
		"        c = Serial.read();\n" +
		"        *str++=c;\n" +
		"    }\n" +
		"    while(c!='\\n');\n" +
		"    return str;\n" +
		"}\n");
			
		ret = codePrefix + "Serialreceive(buffer);\n";
		return ret;
	}
	
	public static void setupSerialEnvironment(Translator t)
	{
		t.addSetupCommand("Serial.begin(9600);");
		t.addDefinitionCommand("char buffer[128];\n\n" +
		"char *Serialreceive(char *str)\n" +
		"{    " +
		"    char c;\n" +
		"    do{\n" +
		"        c = Serial.read();\n" +
		"        *str++=c;\n" +
		"    }\n" +
		"    while(c!='\\n');\n" +
		"    return str;\n" +
		"}\n");
		
	}

}
