package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Toggle extends TranslatorBlock
{
	public static final String ARDUBLOCK_DIGITAL_TOGGLE_DEFINE = "void __ardublockDigitalToggle(int pinNumber)\n{\npinMode(pinNumber, OUTPUT);\ndigitalWrite( pinNumber,!digitalRead( pinNumber));\n}\n\n";
	
	public Toggle(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		
		// Should it ever not use ARDUBLOCK_DIGITAL_TOGGLE_DEFINE? Cannot think why.
		// What is special about NumberBlocks?
		// Should check for "is a simple number" not "instanceof NumberBlock" now that pinLists constants exist 
		// Not sure first part of 'if' should EVER run
//		if (translatorBlock instanceof NumberBlock)
//		{
//			String number = translatorBlock.toCode();
//			String setupCode = "pinMode( " + number + " , OUTPUT);";
//			translator.addSetupCommand(setupCode);
//			
//			String ret = "digitalWrite( ";
//			ret = ret + number;
//			ret = ret + ", !digitalRead(";
//			ret = ret + number;
//			ret = ret + ") );";
//			
//			return ret;
//		}
//		else
//		{
			translator.addDefinitionCommand(ARDUBLOCK_DIGITAL_TOGGLE_DEFINE);
			String ret = "__ardublockDigitalToggle(";
			
			ret = ret + translatorBlock.toCode();
			ret = ret + ");\n";
			return ret;
//		}
		
		
	}
	
}