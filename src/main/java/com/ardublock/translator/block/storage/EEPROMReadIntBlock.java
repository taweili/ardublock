package com.ardublock.translator.block.storage;

//import com.ardublock.core.Context;
import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class EEPROMReadIntBlock extends TranslatorBlock
{
	public EEPROMReadIntBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		setupEEPROMEnvironment(translator);
		
		String ret = "eepromReadInt( ";
		
		TranslatorBlock tb = this.getRequiredTranslatorBlockAtSocket(0);

		ret += tb.toCode() + " ) ";
		
	return codePrefix + ret + codeSuffix;
}
	
	public static void setupEEPROMEnvironment(Translator t)
	{
		t.addHeaderFile("EEPROM.h");
		t.addDefinitionCommand(	"/********************************************************\n" 	+ 
								"A way to read int (2 Bytes)from EEPROM \n" 					+
								"EEPROM library natively supports only bytes\n" 				+                              
								"********************************************************/\n" 	+ 
								"int eepromReadInt(int address){\n" 	+               
                                "\n" 											+        
								"	union u_tag {\n" 							+                                           
								"		byte b[2];\n" 							+                                           
								"		int INTtime;\n" 				+                                
								"	} time;\n" 									+                                                
								"	time.b[0] = EEPROM.read(address);\n" 		+                      
								"	time.b[1] = EEPROM.read(address+1);\n"		+                                      
								"	return time.INTtime;\n" +                                   
								"}\n" );                                                       

	}


}
