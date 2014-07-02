package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class DigitalInputBlock extends TranslatorBlock
{
	public static final String ARDUBLOCK_DIGITAL_READ_DEFINE = "boolean __ardublockDigitalRead(int pinNumber)\n{\npinMode(pinNumber, INPUT);\nreturn digitalRead(pinNumber);\n}\n\n";
	
	public DigitalInputBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		if (translatorBlock instanceof NumberBlock)
		{
			return generateCodeUsingNumberBlock(translatorBlock);
		}
		else
		{
			return generateCodeUsingNonNumberBlock(translatorBlock);
		}
		
	}
	
	protected String generateCodeUsingNumberBlock(TranslatorBlock translatorBlock) throws SocketNullException, SubroutineNotDeclaredException
	{
		String number;
		number = translatorBlock.toCode();
		translator.addInputPin(number.trim());
		
		String ret = "digitalRead(";
		ret = ret + number;
		ret = ret + ")";
		return codePrefix + ret + codeSuffix;
	}
	
	protected String generateCodeUsingNonNumberBlock(TranslatorBlock translatorBlock) throws SocketNullException, SubroutineNotDeclaredException
	{
		translator.addDefinitionCommand(ARDUBLOCK_DIGITAL_READ_DEFINE);
		String ret = "__ardublockDigitalRead(";
		
		ret = ret + translatorBlock.toCode();
		ret = ret + ")";
		return codePrefix + ret + codeSuffix;
	}

}
