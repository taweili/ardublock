package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class RepeatBlock extends TranslatorBlock
{

	public RepeatBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String varName="";//this.getRequiredTranslatorBlockAtSocket(0);
		TranslatorBlock teste = this.getRequiredTranslatorBlockAtSocket(0);
		varName=varName+teste.toCode();
		//translator.addDefinitionCommand("int " + varName + "; //teste");
		String ret = "for (" + varName + "= 1; " + varName + "<= ( ";
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
		ret = ret + translatorBlock.toCode();
		ret = ret + " ); " + varName + "++ )\n{\n";
		
		
		translatorBlock = getTranslatorBlockAtSocket(2);
		while (translatorBlock != null)
		{
			ret = ret + translatorBlock.toCode();
			translatorBlock = translatorBlock.nextTranslatorBlock();
		}
		
		ret = ret + "}\n";
		return ret;
	}

}
