package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

/**
 * 
 * @author heqichen
 *
 * this block just set the port of ir definiton
 */
public class IrSetPortBlock extends TranslatorBlock
{
	
	public IrSetPortBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		translator.registerBodyTranslateFinishCallback(this);
		IrGetCodeBlock.addIrBlockCount(translator);
		
		if (translator.getInternalData(IrGetCodeBlock.IR_SETUP) == null)
		{
			String def = this.getDefinitionCode().append(IrGetCodeBlock.IR_DEFINITION).toString();
			translator.addDefinitionCommand(def);
			translator.addInternalData(IrGetCodeBlock.IR_SETUP, new Object());
		}

		return "";
	}
	
	private StringBuilder getDefinitionCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		return getDefinitionCode(translatorBlock.toCode());
	}
	
	public static StringBuilder getDefinitionCode(String port)
	{

		StringBuilder ret = new StringBuilder();
		ret.append("IRrecv __ab_irrecv(");
		ret.append(port);
		ret.append(");\n");
		return ret;
	}
	
	public void onTranslateBodyFinished() throws SocketNullException, SubroutineNotDeclaredException
	{
		IrGetCodeBlock.irBlockCallback(translator);
	}
	
}
