package com.ardublock.translator.block.dfrobot;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.AnalogInputBlock;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class VolumeSensorBlock extends AnalogInputBlock 
{
	public VolumeSensorBlock(Long blockId, Translator translator,
			String codePrefix, String codeSuffix, String label) {
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	private static final String AB_SOUND_FUN_DEFINE = "" + 
			"int getSoundValue(int port)\n" + 
			"{\n" + 
			"  unsigned long duration = millis() + 20;\n" + 
			"  int maxVol = 0;\n" + 
			"  int currentVol;\n" + 
			"  while (millis() < duration)\n" + 
			"  {\n" + 
			"    currentVol = analogRead(port);\n" + 
			"    if (currentVol > maxVol)\n" + 
			"    {\n" + 
			"      maxVol = currentVol;\n" + 
			"    }\n" + 
			"  }\n" + 
			"  return maxVol;\n" + 
			"}\n\n";
	
	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		translator.addDefinitionCommand(AB_SOUND_FUN_DEFINE);
		String ret = "getSoundValue(";
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		ret = ret + translatorBlock.toCode();
		ret = ret + ")";
		return codePrefix + ret + codeSuffix;
	}
}
