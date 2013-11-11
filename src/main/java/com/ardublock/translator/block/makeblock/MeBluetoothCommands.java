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
		translator.addHeaderFile("Wire.h");
		TranslatorBlock block = this.getRequiredTranslatorBlockAtSocket(0);
		String ret = "MeBluetooth bluetooth"+block.toCode()+"(PORT_"+block.toCode()+");";
		translator.addDefinitionCommand(ret);
		translator.addSetupCommand("bluetooth"+block.toCode()+".begin(9600);");
		TranslatorBlock execBlock = getTranslatorBlockAtSocket(1);
		ret = "";   
		ret += "if(bluetooth"+block.toCode()+".paramAvailable()){\n";
		String exec = "";
		while (execBlock != null)
		{
			exec += "\t"+ execBlock.toCode()+"\n";
			execBlock = execBlock.nextTranslatorBlock();
		}
		ret += "\n"+exec+"\n};\n";
		    
		return ret;
	}

}
