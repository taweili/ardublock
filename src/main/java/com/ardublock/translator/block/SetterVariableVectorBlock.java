package com.ardublock.translator.block;


import java.util.ResourceBundle;
import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class SetterVariableVectorBlock extends TranslatorBlock
{
	private static ResourceBundle uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");
	
	public SetterVariableVectorBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		TranslatorBlock name = this.getRequiredTranslatorBlockAtSocket(0);
		if (!(name instanceof VariableFakeBlock)) {
		      throw new BlockException(blockId, uiMessageBundle.getString("ardublock.error_msg.array_var_slot"));
		    }
		TranslatorBlock position = this.getRequiredTranslatorBlockAtSocket(1);
		TranslatorBlock value = this.getRequiredTranslatorBlockAtSocket(2);
		String ret = name.toCode()+"["+position.toCode()+" - 1]";
		ret = ret + " = " + value.toCode() + " ;\n";
		return ret;
	}

}
