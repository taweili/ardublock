package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class CodeSetupBlock extends TranslatorBlock
{
	public CodeSetupBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		String ret = translatorBlock.toCode();
		//ret=ret.substring(1);
		//ret=ret.replace(ret.substring(ret.length()-1),"");
		ret=ret+"\n";
		translator.addSetupCommandForced(ret);
		return "";
	}
}
