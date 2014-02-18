package com.ardublock.translator.block;

import java.util.ResourceBundle;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class RepeatControlBlock extends TranslatorBlock
{

	private static ResourceBundle uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");
	
	public RepeatControlBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{

		TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);
		if (!(tb instanceof VariableNumberBlock || tb instanceof VariableNumberUnsignedLongBlock || tb instanceof VariableNumberDoubleBlock)) {
			throw new BlockException(blockId, uiMessageBundle.getString("ardublock.error_msg.number_var_slot"));
		}

		String varName = tb.toCode();
		
		tb = this.getRequiredTranslatorBlockAtSocket(1);
		String startVal = tb.toCode();

		tb = this.getRequiredTranslatorBlockAtSocket(2);
		String stopVal = tb.toCode();

		tb = this.getRequiredTranslatorBlockAtSocket(3);
		String incVal = tb.toCode();

		String ret = "";

		ret = ret + "for(" + varName + " = " + startVal + "; \n" + 
				    startVal + "<=" + stopVal + "?" + varName + " <= " + stopVal + ":" + varName + " >= " + stopVal + "; \n" + 
				    startVal + "<=" + stopVal + "?" + varName + " = "  + varName + " + " + incVal + ":" +  varName + " = " + varName + " - " + incVal + ")\n{"; 

		TranslatorBlock translatorBlock = getTranslatorBlockAtSocket(4);
		while (translatorBlock != null)
		{
			ret = ret + translatorBlock.toCode();
			translatorBlock = translatorBlock.nextTranslatorBlock();
		}
		
		ret = ret + "}\n";
		return ret;
	}

}
