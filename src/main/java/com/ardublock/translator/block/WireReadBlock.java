package com.ardublock.translator.block;

import com.ardublock.translator.Translator;

public class WireReadBlock extends TranslatorBlock
{
	protected WireReadBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	public String toCode()
	{
		setupWireEnvironment(translator);
		String ret = "__ardublockI2cReadData( ";
		TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);
		ret = ret + tb.toCode();
		ret = ret + " , ";
		tb = getRequiredTranslatorBlockAtSocket(1);
		ret = ret + tb.toCode();
		ret = ret + " )";
		return codePrefix + ret + codeSuffix;
	}
	
	public static void setupWireEnvironment(Translator t)
	{
		t.addHeaderFile("Wire.h");
		t.addDefinitionCommand("boolean __ardublockIsI2cReadOk;\n\nvoid __ardublockI2cWriteData(int devAddr, int regAddr, int value)\n{\nWire.beginTransmission(devAddr);\nWire.send(regAddr);\nWire.send(value);\nWire.endTransmission();\n}\n\nint __ardublockI2cReadData(int devAddr, int regAddr)\n{\nint data = 0;\nWire.beginTransmission(devAddr);\nWire.send(regAddr);\nWire.endTransmission();\nWire.requestFrom(devAddr);\nif (Wire.availble() > 0)\n{\n__ardublockIsI2cReadOk = true;\ndata = Wire.receive();\n}\nelse\n{\n__ardublockIsI2cReadOk = false;\n}\nreturn data;\n}\n");
		t.addSetupCommand("Wire.begin();\n__ardublockIsI2cReadOk = false;");
	}

}
