package com.ardublock.translator.block.banbao;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class GoBackwardBlock extends TranslatorBlock
{

	public GoBackwardBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label) {
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException,
			SubroutineNotDeclaredException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void afterTranslation()
	{
		if (this.translator.getInternalVar("isBanBaoMotorSetupPut") == null)
		{
			throw new BlockException(blockId, "something not defined");
		}
	}


}
