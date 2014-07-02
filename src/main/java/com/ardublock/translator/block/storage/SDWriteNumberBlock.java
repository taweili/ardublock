package com.ardublock.translator.block.storage;

import com.ardublock.core.Context;
import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class SDWriteNumberBlock extends TranslatorBlock
{
	public SDWriteNumberBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		setupSDEnvironment(translator);
		
		String ret = "";
		
		Context context = Context.getContext();
		if (context.getArduinoVersionString().equals(Context.ARDUINO_VERSION_UNKNOWN))
		{
			ret += "//Unable to detect your Arduino version, using 1.0 in default\n";
		}
		
		TranslatorBlock t1 = getRequiredTranslatorBlockAtSocket(0);
		String b1 = t1.toCode();
		TranslatorBlock t2 = getRequiredTranslatorBlockAtSocket(1);
		String b2 = t2.toCode();
		TranslatorBlock t3 = getRequiredTranslatorBlockAtSocket(2);
		String b3 = t3.toCode();
		//Switch was not used for compatibility with java 1.6

		if (b2.equals("Return")) {

			ret += "__ardublockWriteNumberSDln ( ";
			
        } else {

        	ret += "__ardublockWriteNumberSD ( ";

		}		
			//ret += "__ardublockWriteNumberSD( ";
			ret = ret + b1;
			ret = ret +",";
			ret = ret + b3;
			ret = ret + ");\n";

		return codePrefix + ret + codeSuffix;
	}
	

	//private static final String THERMOCOUPLE_DEFINITION = "#if ARDUINO < 100\nint SCK = 13;\nint MISO = 12;\n#endif\nint CS1 = 11;\nMAX31855 temperature(SCK, CS1, MISO);\n";
	private static final String SD_DEFINITION = "void __ardublockWriteStringSD (String file_name,String text)\n{\nchar Filename[12];\nfile_name.toCharArray(Filename, 12);\nFile dataFile = SD.open(Filename, FILE_WRITE);\n\nif (dataFile)\n{\ndataFile.print(text);\ndataFile.close();\n}\n}\n\nvoid __ardublockWriteNumberSD (String file_name,double number)\n{\nchar Filename[12];\nfile_name.toCharArray(Filename, 12);\nFile dataFile = SD.open(Filename, FILE_WRITE);\n\nif (dataFile)\n{\ndataFile.print(number);\ndataFile.close();\n}\n\n}\n\nvoid __ardublockWriteStringSDln (String file_name,String text)\n{\nchar Filename[12];\nfile_name.toCharArray(Filename, 12);\nFile dataFile = SD.open(Filename, FILE_WRITE);\n\nif (dataFile)\n{\ndataFile.println(text);\ndataFile.close();\n}\n}\n\nvoid __ardublockWriteNumberSDln (String file_name,double number)\n{\nchar Filename[12];\nfile_name.toCharArray(Filename, 12);\nFile dataFile = SD.open(Filename, FILE_WRITE);\n\nif (dataFile)\n{\ndataFile.println(number);\ndataFile.close();\n}\n\n}\n\n";
	private static final String SD_INT_DEFINITION = "void __ardublockWriteNumberIntSD (String file_name,int number)\n{\nchar Filename[12];\nfile_name.toCharArray(Filename, 12);\nFile dataFile = SD.open(Filename, FILE_WRITE);\n\nif (dataFile)\n{\ndataFile.print(number);\ndataFile.close();\n}\n\n}\n\nvoid __ardublockWriteNumberIntSDln (String file_name,int number)\n{\nchar Filename[12];\nfile_name.toCharArray(Filename, 12);\nFile dataFile = SD.open(Filename, FILE_WRITE);\n\nif (dataFile)\n{\ndataFile.println(number);\ndataFile.close();\n}\n\n}\n\n";
	private static final String SD_SETUP_DEFINITION = "const int chipSelect = 10;\nSD.begin(chipSelect);\n";	
	
	public static void setupSDEnvironment(Translator t)
	{
		t.addHeaderFile("SD.h");
		t.addDefinitionCommand(SD_DEFINITION);
		t.addDefinitionCommand(SD_INT_DEFINITION);
		t.addSetupCommand(SD_SETUP_DEFINITION);
	}
	
}
