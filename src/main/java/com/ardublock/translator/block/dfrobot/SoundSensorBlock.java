package com.ardublock.translator.block.dfrobot;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.AnalogInputBlock;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class SoundSensorBlock extends AnalogInputBlock 
{
	public SoundSensorBlock(Long blockId, Translator translator,
			String codePrefix, String codeSuffix, String label) {
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	private static final String AB_SOUND_FUN_DEFINE = ""
			+ "int getSoundValue(int port)\n" + 
			"{\n" + 
			"  int buffer[10];\n" + 
			"  int i;\n" + 
			"  for (i=0; i<10; ++i)\n" + 
			"  {\n" + 
			"    buffer[i] = analogRead(port);\n" + 
			"    delay(1);\n" + 
			"  }\n" + 
			"  sortArray(buffer);\n" + 
			"  \n" + 
			"  return (buffer[1] + buffer[2] + buffer[3]) / 3;\n" + 
			"}\n" + 
			"\n" + 
			"void sortArray(int array[10])\n" + 
			"{\n" + 
			"  int i, j;\n" + 
			"  int maxIndex;\n" + 
			"  int tmp;\n" + 
			"  for (i=0; i<4; ++i)\n" + 
			"  {\n" + 
			"    maxIndex = i;\n" + 
			"    for (j=i+1; j<5; ++j)\n" + 
			"    {\n" + 
			"      if (array[j] > array[maxIndex])\n" + 
			"      {\n" + 
			"        maxIndex = j;\n" + 
			"      }\n" + 
			"    }\n" + 
			"    if (maxIndex > i)\n" + 
			"    {\n" + 
			"      tmp = array[maxIndex];\n" + 
			"      array[maxIndex] = array[i];\n" + 
			"      array[i] = tmp;\n" + 
			"    }\n" + 
			"  }\n" + 
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
