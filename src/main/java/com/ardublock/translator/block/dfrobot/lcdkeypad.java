package com.ardublock.translator.block.dfrobot;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class lcdkeypad extends TranslatorBlock {
	
	public lcdkeypad(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	//@Override
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		// récupération des paramètres du module, ici le message placé en rang 0
		// on écrit donc le code Ã  générer
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0, "lcd.print( ", " );\n");
		// création du texte de code correspondant
		translator.addHeaderFile("LiquidCrystal.h");
		translator.addDefinitionCommand("LiquidCrystal lcd(12, 11, 5, 4, 3, 2);");
		translator.addSetupCommand("lcd.begin(16, 2);");
		String ret = translatorBlock.toCode();
		return ret;
	}
	
}