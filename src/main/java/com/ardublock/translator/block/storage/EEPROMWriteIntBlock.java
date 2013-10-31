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


public class EEPROMWriteIntBlock extends TranslatorBlock
{
//	private static ResourceBundle uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");
	
	public EEPROMWriteIntBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		setupEEPROMEnvironment(translator);

			String ret = "eepromWriteInt( ";
			
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
		t.addDefinitionCommand(	"/*******************************************************************\n"+
								"A way to write an 'int' (2 Bytes) to EEPROM \n"					+
								"EEPROM library natively supports only bytes. \n"						+
								"Note it takes around 8ms to write an int to EEPROM \n"+
								"*******************************************************************/\n"+
								"void eepromWriteInt(int address, int value){\n"						+
								"	union u_tag {\n"													+
								"		byte b[2];        //assumes 2 bytes in an int\n"				+
								"		int INTtime;\n"													+
								"	} time;\n"															+
								"	time.INTtime=value;\n"												+
								"	\n"																	+
								"	EEPROM.write(address  , time.b[0]); \n"								+
								"	EEPROM.write(address+1, time.b[1]); \n"								+
								"}\n" );
	}

}
