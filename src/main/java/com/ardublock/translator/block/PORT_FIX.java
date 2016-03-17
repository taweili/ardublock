package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class PORT_FIX extends TranslatorBlock
{
	public PORT_FIX(Long blockId, Translator translator, String codePrefix,	String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String Pin ;
		String Level0;
		String Level1;
		String Level2;
		String Level3;
		String Level4;
		String Level5;
		String Level6;
		String Level7;
		
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		Pin = translatorBlock.toCode();
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(8);
		Level0 = translatorBlock.toCode();
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(7);
		Level1 = translatorBlock.toCode();
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(6);
		Level2 = translatorBlock.toCode();
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(5);
		Level3 = translatorBlock.toCode();
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(4);
		Level4 = translatorBlock.toCode();
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(3);
		Level5 = translatorBlock.toCode();
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(2);
		Level6 = translatorBlock.toCode();
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
		Level7 = translatorBlock.toCode();
		String ret = Pin + " = B" + Level0 +Level1 +Level2 +Level3 +Level4 +Level5 +Level6 +Level7 + ";\n";
		return ret;
	}
}
