package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

/**
 * 
 * @author heqichen
 *
 * this block just set the port of ir definiton
 */
public class IrGetCodeBlock extends TranslatorBlock
{
	public static final String IR_BLOCK_COUNT = "irBlockCnt";
	public static final String IR_BLOCK_CALLBACK_COUNT = "irBlockCallbackCnt";
	public static final String IR_SETUP = "irSetup";
	
	public static final String IR_DEFINITION = 
			"void __ab_setupIrReceiver()\n" + 
			"{\n" + 
			"  __ab_irrecv.enableIRIn();\n" + 
			"  __ab_irrecv.resume();\n" + 
			"}\n" +
			"void charsToUpper(char *str)\n" + 
			"{\n" + 
			"  int p=0;\n" + 
			"  while(str[p] != 0)\n" + 
			"  {\n" + 
			"    str[p] = toupper(str[p]);\n" + 
			"    ++p;\n" + 
			"  }\n" + 
			"}\n" + 
			"void __ab_getIrCommand(char *receivedCommand)\n" + 
			"{\n" + 
			"  decode_results result;\n" + 
			"  if (__ab_irrecv.decode(&result))\n" + 
			"  {\n" + 
			"    ltoa(result.value, receivedCommand, 16);\n" + 
			"    charsToUpper(receivedCommand);\n" + 
			"    __ab_irrecv.resume();\n" + 
			"  }\n" + 
			"  else\n" + 
			"  {\n" + 
			"    receivedCommand[0] = '\\0';\n" + 
			"  }\n" + 
			"}";
	
	
	public IrGetCodeBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException, BlockException
	{
		addIrBlockCount(translator);
		translator.registerBodyTranslateFinishCallback(this);
		
		translator.addHeaderFile("IRremote00.h");
		translator.addHeaderFile("ctype.h");
		translator.addHeaderFile("Wire.h");
		
		translator.addSetupCommand("__ab_setupIrReceiver();");
		
		TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);
		
		if (tb instanceof MessageBlock)
		{
			throw new BlockException(tb.blockId, "message cannot be override, choose string variable instead");
		}
		
		return codePrefix + "__ab_getIrCommand(" + tb.toCode() + ");\n" + codeSuffix;
	}
	
	public static void addIrBlockCount(Translator translator)
	{
		IrGetCodeBlock.increaseInternalData(translator, IrGetCodeBlock.IR_BLOCK_COUNT);
	}
	
	public static void addIrBlockCallbackCount(Translator translator)
	{
		IrGetCodeBlock.increaseInternalData(translator, IrGetCodeBlock.IR_BLOCK_CALLBACK_COUNT);
	}
	
	public static void increaseInternalData(Translator translator, String name)
	{
		Object o = translator.getInternalData(name);
		if (o == null)
		{
			Integer i = new Integer(1);
			translator.addInternalData(name, i);
		}
		else
		{
			Integer i = (Integer)(o);
			i = Integer.valueOf(i.intValue() + 1);
			translator.addInternalData(name, i);
		}
	}
	
	public static boolean isLastIrBlock(Translator translator)
	{
		Integer registeredBlockCount = (Integer)translator.getInternalData(IrGetCodeBlock.IR_BLOCK_COUNT);
		Integer currentBlockCount = (Integer)translator.getInternalData(IrGetCodeBlock.IR_BLOCK_CALLBACK_COUNT);
		if (registeredBlockCount.intValue() == currentBlockCount.intValue())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static void irBlockCallback(Translator translator)
	{
		IrGetCodeBlock.addIrBlockCallbackCount(translator);
		if (isLastIrBlock(translator) && translator.getInternalData(IrGetCodeBlock.IR_SETUP)==null)
		{
			String def = IrSetPortBlock.getDefinitionCode("11").append(IrGetCodeBlock.IR_DEFINITION).toString();
			translator.addDefinitionCommand(def);
		}
	}
	public void onTranslateBodyFinished()
	{
		IrGetCodeBlock.irBlockCallback(translator);
	}
	
	
}
