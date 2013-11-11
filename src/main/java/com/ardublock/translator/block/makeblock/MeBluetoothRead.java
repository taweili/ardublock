package com.ardublock.translator.block.makeblock;
import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class MeBluetoothRead extends TranslatorBlock {

	public MeBluetoothRead(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException {
		translator.addHeaderFile("Makeblock.h");
		translator.addHeaderFile("SoftwareSerial.h");
		translator.addHeaderFile("Wire.h");
		TranslatorBlock block = this.getRequiredTranslatorBlockAtSocket(0);
		String ret = "MeBluetooth bluetooth"+block.toCode()+"(PORT_"+block.toCode()+");";
		translator.addDefinitionCommand(ret);
		translator.addSetupCommand("bluetooth"+block.toCode()+".begin(9600);");
		TranslatorBlock dataBlock = this.getRequiredTranslatorBlockAtSocket(1);
		TranslatorBlock execBlock = this.getTranslatorBlockAtSocket(2);
		String exec = "";
		if(execBlock!=null){
			while (execBlock != null)
			{
				exec += "\t"+ execBlock.toCode()+"\n";
				execBlock = execBlock.nextTranslatorBlock();
			}
		}
		return dataBlock.toCode()+"= bluetooth"+block.toCode()+".read();\nif("+dataBlock.toCode()+">-1){\n\t"+exec+"\n}\n";
	}

}
