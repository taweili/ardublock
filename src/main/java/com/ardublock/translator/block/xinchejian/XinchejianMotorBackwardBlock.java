package com.ardublock.translator.block.xinchejian;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;

public class XinchejianMotorBackwardBlock extends TranslatorBlock
{
	public XinchejianMotorBackwardBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	public String toCode()
	{
		translator.addDefinitionCommand(XinchejianMotorForwardBlock.ARDUBLOCK_MOTOR_DEFINITION);
		
		String ret = "__ardublock_xinchejian_ms( ";
		TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);
		ret = ret + tb.toCode();
		ret = ret + " , false, ";
		tb = this.getRequiredTranslatorBlockAtSocket(1);
		ret = ret + tb.toCode();
		ret = ret + " );\n";
		
		return ret;
	}

}
