package com.ardublock.translator.block.makeblock;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.NumberBlock;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class MeMotorDriver extends TranslatorBlock {

	public MeMotorDriver(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException {
		translator.addHeaderFile("Makeblock.h");
		translator.addHeaderFile("SoftwareSerial.h");
		translator.addHeaderFile("Wire.h");
		TranslatorBlock port = this.getTranslatorBlockAtSocket(0);
		TranslatorBlock me = this.getTranslatorBlockAtSocket(1);
		
		int portNum = port==null?0:Integer.parseInt(port.toCode());
		TranslatorBlock block = portNum>0?port:me;
		String motor = "dcMotor"+block.toCode();
		String ret = "MeDCMotor "+motor+(portNum>0?"(PORT_":"(M")+block.toCode()+");";
		translator.addDefinitionCommand(ret);
		block = this.getRequiredTranslatorBlockAtSocket(2);
		if(block instanceof NumberBlock){
			int speed = Integer.parseInt(block.toCode());
			speed = speed>255?255:(speed<-255?-255:speed);
			if(speed==0){
				return motor+".stop();\n";
			}else{
				return motor+".run("+speed+");\n";
			}
		}else{
			return motor+".run("+block.toCode()+");\n";
		}
		
	}

}
