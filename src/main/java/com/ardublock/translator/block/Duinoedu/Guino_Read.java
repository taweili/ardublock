package com.ardublock.translator.block.Duinoedu;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Guino_Read  extends TranslatorBlock {

	public Guino_Read (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	//@Override
		public String toCode() throws SocketNullException, SubroutineNotDeclaredException
		{
			
			String Title;
			String Variable;
			String Min;
			String Max;
			TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
			Title = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
			Variable = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
			Min = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
			Max = translatorBlock.toCode();
			
			translator.addHeaderFile("EasyTransfer.h");
			translator.addHeaderFile("EEPROM.h");
			translator.addHeaderFile("Guino.h");
			translator.addDefinitionCommand("//libraries at http://duinoedu.com/dl/lib");
			translator.addSetupCommand("GUINO_BRANCHER();");
			translator.addGuinoCommand("GUINO_AFFICHER_GRAPH("+Title+","+Variable+","+Min+","+Max+");\nGUINO_AFFICHER_LIGNE(); ");
			
			
			String ret =  "GUINO_LIRE("+Variable+");";
			return  ret ;
			
		}
}
