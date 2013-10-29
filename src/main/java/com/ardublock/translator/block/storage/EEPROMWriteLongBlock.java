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


public class EEPROMWriteLongBlock extends TranslatorBlock
{
//	private static ResourceBundle uiMessageBundle = ResourceBundle.getBundle("com/ardublock/block/ardublock");
	
	public EEPROMWriteLongBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		setupEEPROMEnvironment(translator);

			String ret = "eepromWriteLong( ";
			
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
		t.addDefinitionCommand(	"/*******************************************************************\n"	+
								"A way to write unsigned longs (4 Bytes) to EEPROM \n"					+
								"EEPROM library natively supports only bytes. \n"						+
								"Some attempt to save time but note takes around 8ms to write a long \n"+
								"*******************************************************************/\n"+
								"void eepromWriteLong(int address, unsigned long value){\n"				+
								"	union u_tag {\n"													+
								"		byte b[4];        //assumes 4 bytes in an long\n"				+
								"		unsigned long ULtime;\n"										+
								"	} time;\n"															+
								"	time.ULtime=value;\n"												+
								"	// Do some checking if !SLOW! EEPROM writes are actually needed\n"	+
								"	EEPROM.write(address  , time.b[0]); //Very likely to change. Not worth checking\n"	+
								"	EEPROM.write(address+1, time.b[1]); //Very likely to change. Not worth checking\n"	+
								"	if (time.b[2] != EEPROM.read(address+2) ) EEPROM.write(address+2, time.b[2]); // Save time/W&T. Don't write unless changed\n"	+
								"	if (time.b[3] != EEPROM.read(address+3) ) EEPROM.write(address+3, time.b[3]); // Save time/W&T. Don't write unless changed\n"	+
								"}\n" );
	}

}
