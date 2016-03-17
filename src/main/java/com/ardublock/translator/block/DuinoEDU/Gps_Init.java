package com.ardublock.translator.block.Duinoedu;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Gps_Init  extends TranslatorBlock {

	public Gps_Init (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	//@Override
		public String toCode() throws SocketNullException, SubroutineNotDeclaredException
		{
			String connect;
			String jetlag;
			String Interruption;
			String Info;
			String Frequence;
			
			TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
			connect = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
			Interruption = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(2);
			jetlag = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(3);
			Info = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(4);
			Frequence = translatorBlock.toCode();
			
			translator.addHeaderFile("Adafruit_GPS.h");
			translator.addHeaderFile("SoftwareSerial.h");
			translator.addHeaderFile("duinoeduGpsAdd.h");
			translator.addDefinitionCommand("//libraries at http://duinoedu.com/dl/lib/dupont/EDU_Adafruit_GPS/ \n//libraries at http://duinoedu.com/dl/lib/dupont/EDU_duinoeduGpsAdd/\n "
					+ "duinoeduGpsAdd monGps;\n"
					+ "EDUGPS_INTERRUPTION(HORLOGE_0_DEPASSE_VALEUR_A) {\n"
					+ "  monGps.lireUnCaractere();\n"
					+ "}\n");
			translator.addSetupCommand(connect+Interruption+"monGps.definirDecalageHoraire("+jetlag+");");
			translator.addSetupCommand("monGps.definirQuantiteInformation("+Info+");");
			translator.addSetupCommand("monGps.definirFrequenceTrames(EDUGPS_"+Frequence+"HZ);");
			String ret;
			
			 ret =  "";
	

		return ret ;	
		}
}
