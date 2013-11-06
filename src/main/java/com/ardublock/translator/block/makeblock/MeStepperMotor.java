package com.ardublock.translator.block.makeblock;
import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class MeStepperMotor extends TranslatorBlock {

	public MeStepperMotor(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException {
		translator.addHeaderFile("Makeblock.h");
		translator.addHeaderFile("SoftwareSerial.h");
		translator.addHeaderFile("Wire.h");
		TranslatorBlock portBlock = this.getRequiredTranslatorBlockAtSocket(0);
		String ret = "MeStepperMotor stepper"+portBlock.toCode()+"(PORT_"+portBlock.toCode()+");";
		translator.addDefinitionCommand(ret);
		TranslatorBlock speedBlock = this.getRequiredTranslatorBlockAtSocket(1);
		TranslatorBlock accelerationBlock = this.getRequiredTranslatorBlockAtSocket(2);
		translator.addSetupCommand("stepper"+portBlock.toCode()+".begin(STP_SIXTEENTH,"+speedBlock.toCode()+","+accelerationBlock.toCode()+");");
		TranslatorBlock moveToBlock = this.getRequiredTranslatorBlockAtSocket(3);
		TranslatorBlock execBlock = getTranslatorBlockAtSocket(4);
		ret = "stepper"+portBlock.toCode()+".moveTo("+moveToBlock.toCode()+");\n";
		ret += "int distance = stepper"+portBlock.toCode()+".distanceToGo();\n";     
		ret += "if(distance==0){\n";
		String exec = "";
		while (execBlock != null)
		{
			exec += "\t"+ execBlock.toCode()+"\n";
			execBlock = execBlock.nextTranslatorBlock();
		}
		ret += exec+"\n}";
		    
		return ret;
	}

}
