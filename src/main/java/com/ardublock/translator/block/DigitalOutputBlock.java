package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class DigitalOutputBlock extends TranslatorBlock
{
	public static final String ARDUBLOCK_DIGITAL_WRITE_DEFINE = "void __ardublockDigitalWrite(int pinNumber, boolean status)\n{\npinMode(pinNumber, OUTPUT);\ndigitalWrite(pinNumber, status);\n}\n";
	
	public DigitalOutputBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		if (translatorBlock instanceof NumberBlock)
		{
			String number = translatorBlock.toCode();
			String setupCode = "\tpinMode( " + number + " , OUTPUT);";
			translator.addSetupCommand(setupCode);
			
			String ret = "\tdigitalWrite( ";
			ret = ret + number;
			ret = ret + " , ";
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
			ret = ret + translatorBlock.toCode();
			ret = ret + " );\n";
			return ret;
		}
		else
		{
			translator.addDefinitionCommand(ARDUBLOCK_DIGITAL_WRITE_DEFINE);
			String ret = "__ardublockDigitalWrite(";
			
			ret = ret + translatorBlock.toCode();
			ret = ret + ", ";
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
			ret = ret + translatorBlock.toCode();
			ret = ret + ");\n";
			return ret;
		}
		
	}

}
