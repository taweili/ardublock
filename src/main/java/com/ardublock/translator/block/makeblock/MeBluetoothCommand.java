package com.ardublock.translator.block.makeblock;
import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class MeBluetoothCommand extends TranslatorBlock {

	public MeBluetoothCommand(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException {
		
		TranslatorBlock block = this.getRequiredTranslatorBlockAtSocket(0);
		int index = Integer.parseInt(block.toCode());
		if(index==0){
			return "(btCommands[1]&0xf0)>>4";//module
		}else if(index==1){
			return "btCommands[1]&0xf";//deviceId
		}else if(index==2){
			return "btCommands[2]";//mode
		}else if(index==3){
			return "btCommands[3]";//port
		}else if(index==4){
			return "(btCommands[4]==1?1:-1)*(btCommands[5]*100+btCommands[6])";//value
		}
		return "0";
	}

}
