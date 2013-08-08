package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class CreateVariableVectorBlock extends TranslatorBlock
{
	public CreateVariableVectorBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String varName="";
		TranslatorBlock name = this.getRequiredTranslatorBlockAtSocket(0);
		TranslatorBlock size = this.getRequiredTranslatorBlockAtSocket(1);
		varName+=name.toCode();
		int foo = Integer.parseInt(size.toCode())+1;
		varName+="[";
		translator.addDefinitionCommand("int " + varName +foo+"];\n");
		translator.addSetupCommand("\tfor (int i=0;i<"+foo+";i++) "+ varName+"i]=0;\n"   );
		return "";
	}

}
