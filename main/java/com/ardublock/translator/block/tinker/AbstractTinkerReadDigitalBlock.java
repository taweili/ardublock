package com.ardublock.translator.block.tinker;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.DigitalInputBlock;
import com.ardublock.translator.block.NumberBlock;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public abstract class AbstractTinkerReadDigitalBlock extends TranslatorBlock
{

	AbstractTinkerReadDigitalBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
		translator.addHeaderFile("TinkerKit.h");
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		if (translatorBlock instanceof TinkerInputPortBlock)
		{
			String number = translatorBlock.toCode();
			return codePrefix + "( analogRead(" + number + ")>512?true:false)" + codeSuffix;
		}
		else
		{
			if (translatorBlock instanceof NumberBlock)
			{
				String number;
				number = translatorBlock.toCode();
				String setupCode = "pinMode( " + number + " , INPUT);";
				translator.addSetupCommand(setupCode);
				String ret = "digitalRead( ";
				ret = ret + number;
				ret = ret + ")";
				return codePrefix + ret + codeSuffix;
			}
			else
			{
				translator.addDefinitionCommand(DigitalInputBlock.ARDUBLOCK_DIGITAL_READ_DEFINE);
				String ret = "__ardublockDigitalRead(";
				
				ret = ret + translatorBlock.toCode();
				ret = ret + ")";
				return codePrefix + ret + codeSuffix;
			}
		}
	}
	
	
	

}
