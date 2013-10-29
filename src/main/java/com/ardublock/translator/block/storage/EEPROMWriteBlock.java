package com.ardublock.translator.block.storage;

//import java.util.ResourceBundle;
//import com.ardublock.core.Context;
import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
//import com.ardublock.translator.block.NumberBlock;
//import com.ardublock.translator.block.VariableNumberBlock;
//import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;


public class EEPROMWriteBlock extends TranslatorBlock
{
//	private static ResourceBundle uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");
	
	public EEPROMWriteBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		setupEEPROMEnvironment(translator);

			String ret = "EEPROM.write( ";
			
			TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);
			//if (!(tb instanceof VariableNumberBlock) && !(tb instanceof NumberBlock)) {
				
			//	throw new BlockException(blockId, uiMessageBundle.getString("ardublock.error_msg.number_int_slot"));
			//}
			
			ret += tb.toCode();
			tb = this.getRequiredTranslatorBlockAtSocket(1);
			ret = "\t"+ret + " , " + tb.toCode() + " ) ;\n";
			
		return codePrefix + ret + codeSuffix;
	}
	
	private static void setupEEPROMEnvironment(Translator t)
	{
		t.addHeaderFile("EEPROM.h");
	}

}
