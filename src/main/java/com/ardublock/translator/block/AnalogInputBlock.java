package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.bbbb.BbbbBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class AnalogInputBlock extends TranslatorBlock
{
	public AnalogInputBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		
		
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		String number = translatorBlock.toCode().trim();
		String ret = String.format("bbAnalogRead(%s)", number);
		
		translator.addDefinitionCommand(BbbbBlock.BBBB_DEF);
		translator.addSetupCommand("lightOnIndicator(" + number + ");");
		
		return codePrefix + ret + codeSuffix;
	}

}
