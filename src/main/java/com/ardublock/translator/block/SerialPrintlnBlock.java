package com.ardublock.translator.block;


import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class SerialPrintlnBlock extends TranslatorBlock
{
	public SerialPrintlnBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		translator.addSetupCommand("Serial.begin(9600);");
		TranslatorBlock translatorBlock = this.getTranslatorBlockAtSocket(0, "Serial.print(", ");\n");
		
		String ret = "";
		if (translatorBlock != null) {
			ret = translatorBlock.toCode();
		}
		ret = ret + "Serial.println();\n";
		
		return ret;
	}
}
