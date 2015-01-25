package com.ardublock.translator.block.seeedstudio;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.NumberBlock;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class GroveJoyStickButtonBlock extends TranslatorBlock
{
	public GroveJoyStickButtonBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String ret = "analogRead(A";
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		
			ret = ret + translatorBlock.toCode();
			ret = ret + ") > 1000";
			return codePrefix + ret + codeSuffix;
		
		
	}
}
