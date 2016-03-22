package com.ardublock.translator.block.Duinoedu;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class RTC_Mem  extends TranslatorBlock {

	public RTC_Mem (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	//@Override
		public String toCode() throws SocketNullException, SubroutineNotDeclaredException
		{
			String Day;
			String Month;
			String Year;
			String Hour;
			String Minute;
			String Second;
			TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
			Day = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
			Month = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(2);
			Year = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(3);
			Hour = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(4);
			Minute = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(5);
			Second = translatorBlock.toCode();
			
			translator.addHeaderFile("Wire.h");
			translator.addHeaderFile("DS1307.h");
			translator.addDefinitionCommand("DS1307 clock;");
			translator.addSetupCommand("clock.brancher();\n"
					+ "clock.ecrireDate("+Day+","+Month+","+Year+");\n"
					+ "clock.ecrireHeure("+Hour+","+Minute+","+Second+");\n");
			
			return "";
		}
}
