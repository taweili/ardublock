package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;

public class SerialReadBlock extends TranslatorBlock
{
	protected SerialReadBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	public String toCode() throws SocketNullException
	{
		/*setupWireEnvironment(translator);
		String ret = "__ardublockI2cReadData( ";
		TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);
		ret = ret + tb.toCode();
		ret = ret + " , ";
		tb = getRequiredTranslatorBlockAtSocket(1);
		ret = ret + tb.toCode();
		ret = ret + " )";
		return codePrefix + ret + codeSuffix;*/
		
		String ret = codePrefix + "Serial.read()";
		return ret;
	}
	
	public static void setupWireEnvironment(Translator t)
	{
		//t.addHeaderFile("Wire.h");
		//t.addDefinitionCommand("boolean __ardublockIsI2cReadOk;\n\nvoid __ardublockI2cWriteData(int devAddr, int regAddr, int value)\n{\nWire.beginTransmission(devAddr);\nWire.write(regAddr);\nWire.write(value);\nWire.endTransmission();\n}\n\nint __ardublockI2cReadData(int devAddr, int regAddr)\n{\nint data = 0;\nchar b;\nWire.beginTransmission(devAddr);\nWire.write(regAddr);\nWire.endTransmission();\nWire.requestFrom(devAddr, 1);\nif (Wire.available() > 0)\n{\n__ardublockIsI2cReadOk = true;\nb = Wire.read();\ndata = b;\n}\nelse\n{\n__ardublockIsI2cReadOk = false;\n}\nreturn data;\n}\n");
		//t.addSetupCommand("Wire.begin();\n__ardublockIsI2cReadOk = false;");

	}

}

