// Not now used by standard blocks. Retained as long as it is referenced by legacy blocks.
package com.ardublock.translator.block.molegraph;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class ArduinoToSerial extends TranslatorBlock
{

	public ArduinoToSerial(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		
		translator.addHeaderFile("ArduinoToSerial.h");
		translator.addDefinitionCommand("ArduinoToSerial arduinoToSerial;");
		translator.addSetupCommand("arduinoToSerial.Setup();\n" + 
		"arduinoToSerial.SetSendingCallback(&UpdateGraphChannels);\n" + 
		"arduinoToSerial.SetMeasurementStartedCallback(&MeasurementStartedCallback);\n" +
		"arduinoToSerial.SetMeasurementStoppedCallback(&MeasurementStoppedCallback);\n" +
		"arduinoToSerial.SetMeasurementPausedCallback(&MeasurementPausedCallback);\n" +
		"arduinoToSerial.SetMeasurementContinuedCallback(&MeasurementContinuedCallback);\n" + 
		"pinMode(13, OUTPUT);");
		
        String ch1 = translatorBlock.toCode();
		translatorBlock = getRequiredTranslatorBlockAtSocket(1);
		String ch2 = translatorBlock.toCode();
		translatorBlock = getRequiredTranslatorBlockAtSocket(2);
		String ch3 = translatorBlock.toCode();
		translatorBlock = getRequiredTranslatorBlockAtSocket(3);
		String ch4 = translatorBlock.toCode();
		translatorBlock = getRequiredTranslatorBlockAtSocket(4);
		String ch5 = translatorBlock.toCode();
		translatorBlock = getRequiredTranslatorBlockAtSocket(5);
		String ch6 = translatorBlock.toCode();
		translatorBlock = getRequiredTranslatorBlockAtSocket(6);
		String ch7 = translatorBlock.toCode();
		translatorBlock = getRequiredTranslatorBlockAtSocket(7);
		String ch8 = translatorBlock.toCode();

		translator.addDefinitionCommand("\n" + 
		        "void UpdateGraphChannels(void)\n" +
				"{\n" + 
			    "arduinoToSerial.SetChannelValue(1, " + ch1 + ");\n" +
			    "arduinoToSerial.SetChannelValue(2, " + ch2 + ");\n" +
			    "arduinoToSerial.SetChannelValue(3, " + ch3 + ");\n" +
			    "arduinoToSerial.SetChannelValue(4, " + ch4 + ");\n" +
			    "arduinoToSerial.SetChannelValue(5, " + ch5 + ");\n" +
			    "arduinoToSerial.SetChannelValue(6, " + ch6 + ");\n" +
			    "arduinoToSerial.SetChannelValue(7, " + ch7 + ");\n" +
			    "arduinoToSerial.SetChannelValue(8, " + ch8 + ");\n" +
			    "}");
		
		translator.addDefinitionCommand("void MeasurementStartedCallback(void)\n" +
		        "{\n" +
			    "digitalWrite(13, HIGH);\n" + 
			    "}\n" +
			    "void MeasurementStoppedCallback(void)\n" +
			    "{\n" +
			    "digitalWrite(13, LOW);\n" +
			    "}\n" +
			    "void MeasurementPausedCallback(void)\n" +
			    "{\n" +
			    "digitalWrite(13, LOW);\n" +
			    "}\n" +
			    "void MeasurementContinuedCallback(void)\n" +
		        "{\n" +
			    "digitalWrite(13, HIGH);\n" + 
			    "}\n");
		
		String ret = "arduinoToSerial.InLoop();\n";
		return ret;
	}

}
