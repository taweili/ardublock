package com.ardublock.translator.block.storage;

import com.ardublock.core.Context;
import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class EEPROMWriteBlock extends TranslatorBlock
{
	public EEPROMWriteBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		setupEEPROMEnvironment(translator);
		
		String ret = "";
		
		Context context = Context.getContext();
		if (context.getArduinoVersionString().equals(Context.ARDUINO_VERSION_UNKNOWN))
		{
			ret += "//Unable to detect your Arduino version, using 1.0 in default\n";
		}

		TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);		
		TranslatorBlock tb1 = this.getRequiredTranslatorBlockAtSocket(1);
		
		//Switch was not used for compatibility with java 1.6

			ret += "EEPROM.write( ";
			int devAddr1 = Integer.parseInt(tb.toCode());
			if(devAddr1>1023){
			devAddr1 = 1023;
			}
			ret = ret + Integer.toString(devAddr1);
			ret = ret + " , ";
			int data1 = Integer.parseInt(tb1.toCode());
			if(data1>128){
			data1 = 128;
			}
			ret = ret + data1;
			ret = ret + " );\n";

		return codePrefix + ret + codeSuffix;
	}
	
	private static void setupEEPROMEnvironment(Translator t)
	{
		t.addHeaderFile("EEPROM.h");
	}

}
