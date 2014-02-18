package com.ardublock.translator.block;

import java.util.ResourceBundle;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class RepeatBlock extends TranslatorBlock
{

	private static ResourceBundle uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");
	
	public RepeatBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		String varName="";//this.getRequiredTranslatorBlockAtSocket(0);
		TranslatorBlock teste = this.getRequiredTranslatorBlockAtSocket(0);
		if (!(teste instanceof VariableNumberBlock || teste instanceof VariableNumberUnsignedLongBlock || teste instanceof VariableNumberDoubleBlock)) {
			throw new BlockException(blockId, uiMessageBundle.getString("ardublock.error_msg.number_var_slot"));
		}
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
