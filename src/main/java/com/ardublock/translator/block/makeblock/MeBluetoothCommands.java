package com.ardublock.translator.block.makeblock;
import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class MeBluetoothCommands extends TranslatorBlock {

	public MeBluetoothCommands(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException {
		translator.addHeaderFile("Makeblock.h");
		translator.addHeaderFile("SoftwareSerial.h");
		translator.addHeaderFile("Servo.h");
		translator.addHeaderFile("Wire.h");
		TranslatorBlock block = this.getRequiredTranslatorBlockAtSocket(0);
		String ret = "MeBluetooth bluetooth"+block.toCode()+"(PORT_"+block.toCode()+");";
		translator.addDefinitionCommand(ret);
		translator.addDefinitionCommand("int bufferIndex=0;");
		translator.addDefinitionCommand("int btRead=-1;");
		translator.addDefinitionCommand("unsigned char btCommands[10] = {};");
		translator.addSetupCommand("bluetooth"+block.toCode()+".begin(9600);");
		TranslatorBlock execBlock = getTranslatorBlockAtSocket(1);
		ret = "";
		ret += "btRead = bluetooth"+block.toCode()+".read();\n";     
		ret += "if(btRead!=-1){\nif(btRead==0xff)bufferIndex=0;";
		ret += "btCommands[bufferIndex]=(unsigned char)(btRead&0xff);\n"; 
		ret += "bufferIndex++;\n";
		String exec = "";
		while (execBlock != null)
		{
			exec += "\t"+ execBlock.toCode()+"\n";
			execBlock = execBlock.nextTranslatorBlock();
		}
		ret += "if(btRead==0xfe){\n"+exec+"\n}\n";
		ret += "if(bufferIndex>10)bufferIndex = 0;\n}";
		    
		return ret;
	}

}
