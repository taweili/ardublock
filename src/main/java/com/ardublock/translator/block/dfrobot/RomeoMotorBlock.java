// Not now used by standard blocks. Retained as long as it is referenced by legacy blocks.
package com.ardublock.translator.block.dfrobot;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.NumberBlock;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class RomeoMotorBlock extends TranslatorBlock
{
	
	public RomeoMotorBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		RomeoMotorsBlock.setupRomeoMotorPin(translator);
		
		String ret = "setRomeoMotor(";
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		ret += translatorBlock.toCode();
		ret += ", ";
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
		ret += translatorBlock.toCode();
		ret += ");\n";

		return ret;
	}

}
