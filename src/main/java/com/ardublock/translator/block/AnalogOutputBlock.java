package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.bbbb.BbbbBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class AnalogOutputBlock extends TranslatorBlock
{
	public AnalogOutputBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		String number = translatorBlock.toCode().trim();
		if (translatorBlock instanceof NumberBlock)
		{
			translator.addDefinitionCommand(BbbbBlock.BBBB_DEF);
			translator.addSetupCommand("lightOnIndicator(" + number + ");");
		}
		else
		{
			translator.addSetupCommand(String.format("pinMode(%s, OUTPUT);", number));
		}
		
		String status = this.getRequiredTranslatorBlockAtSocket(1).toCode().trim();
		
		String ret = String.format("bbAnalogWrite(%s, %s);", number, status);
		return ret;
	}

}


