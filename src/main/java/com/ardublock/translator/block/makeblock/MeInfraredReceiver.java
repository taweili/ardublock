package com.ardublock.translator.block.makeblock;
import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class MeInfraredReceiver extends TranslatorBlock {

	public MeInfraredReceiver(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException {
		translator.addHeaderFile("Makeblock.h");
		translator.addHeaderFile("SoftwareSerial.h");
		translator.addHeaderFile("Wire.h");
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		String ret = "MeInfraredReceiver infraredReceiver"+translatorBlock.toCode()+"(PORT_"+translatorBlock.toCode()+");";
		translator.addDefinitionCommand(ret);
		translator.addSetupCommand("infraredReceiver"+translatorBlock.toCode()+".begin();");
		

		TranslatorBlock dataBlock = this.getRequiredTranslatorBlockAtSocket(1);
		TranslatorBlock keyDownBlock = getTranslatorBlockAtSocket(2);
		TranslatorBlock keyUpBlock = getTranslatorBlockAtSocket(3);
		ret = "\nif(infraredReceiver"+translatorBlock.toCode()+".buttonState()==1){\n"+dataBlock.toCode()+"=infraredReceiver"+translatorBlock.toCode()+".read();\n";
		String exec = "";
		while (keyDownBlock != null)
		{
			exec += "\t"+ keyDownBlock.toCode()+"\n";
			keyDownBlock = keyDownBlock.nextTranslatorBlock();
		}
		ret += "\n"+exec+"\n}else{\n";
		exec = "";
		while (keyUpBlock != null)
		{
			exec += "\t"+ keyUpBlock.toCode()+"\n";
			keyUpBlock = keyUpBlock.nextTranslatorBlock();
		}
		ret += "\n"+exec+"\n};\n";
		return ret;
	}

}
