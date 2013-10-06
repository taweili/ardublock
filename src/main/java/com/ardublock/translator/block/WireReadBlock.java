package com.ardublock.translator.block;

import com.ardublock.core.Context;
import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class WireReadBlock extends TranslatorBlock
{
	public WireReadBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		setupWireEnvironment(translator);
		
		String ret = "";
		
		Context context = Context.getContext();
		if (context.getArduinoVersionString().equals(Context.ARDUINO_VERSION_UNKNOWN))
		{
			ret += "//Unable to dectect your Arduino version, using 1.0 in default\n";
		}
		ret += "__ardublockI2cReadData( ";
		TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);
		ret = ret + tb.toCode();
		ret = ret + " , ";
		tb = getRequiredTranslatorBlockAtSocket(1);
		ret = ret + tb.toCode();
		ret = ret + " )";
		return codePrefix + ret + codeSuffix;
	}
	
	private static final String IIC_DEFINITION_10 = "boolean __ardublockIsI2cReadOk;\n\nvoid __ardublockI2cWriteData(int devAddr, int regAddr, int value)\n{\nWire.beginTransmission(devAddr);\nWire.write(regAddr);\nWire.write(value);\nWire.endTransmission();\n}\n\nvoid __ardublockI2cWriteDataOne(int devAddr, int regAddr)\n{\nWire.beginTransmission(devAddr);\nWire.write(regAddr);\nWire.endTransmission();\n}\n\nint __ardublockI2cReadData(int devAddr, int regAddr)\n{\nint data = 0;\nchar b;\nWire.beginTransmission(devAddr);\nWire.write(regAddr);\nWire.endTransmission();\nWire.requestFrom(devAddr, 1);\nif (Wire.available() > 0)\n{\n__ardublockIsI2cReadOk = true;\nb = Wire.read();\ndata = b;\n}\nelse\n{\n__ardublockIsI2cReadOk = false;\n}\nreturn data;\n}\n";
	private static final String IIC_DEFINITION_00 = "boolean __ardublockIsI2cReadOk;\n\nvoid __ardublockI2cWriteData(int devAddr, int regAddr, int value)\n{\nWire.beginTransmission(devAddr);\nWire.send(regAddr);\nWire.send(value);\nWire.endTransmission();\n}\n\nvoid __ardublockI2cWriteDataOne(int devAddr, int regAddr)\n{\nWire.beginTransmission(devAddr);\nWire.send(regAddr);\nWire.endTransmission();\n}\n\nint __ardublockI2cReadData(int devAddr, int regAddr)\n{\nint data = 0;\nchar b;\nWire.beginTransmission(devAddr);\nWire.send(regAddr);\nWire.endTransmission();\nWire.requestFrom(devAddr, 1);\nif (Wire.available() > 0)\n{\n__ardublockIsI2cReadOk = true;\nb = Wire.receive();\ndata = b;\n}\nelse\n{\n__ardublockIsI2cReadOk = false;\n}\nreturn data;\n}\n";
	
	public static void setupWireEnvironment(Translator t)
	{
		t.addHeaderFile("Wire.h");
		
		Context context = Context.getContext();
		String arduinoVersion = context.getArduinoVersionString();
		if (arduinoVersion.startsWith("0"))
		{
			t.addDefinitionCommand(IIC_DEFINITION_00);
		}
		else
		{
			if (arduinoVersion.startsWith("1"))
			{
				t.addDefinitionCommand(IIC_DEFINITION_10);
			}
			else
			{
				t.addDefinitionCommand(IIC_DEFINITION_10);
			}
		}
		
		t.addSetupCommand("\tWire.begin();\n\t__ardublockIsI2cReadOk = false;");
	}

}
