package com.ardublock.translator.block.Duinoedu;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Matrice_clear  extends TranslatorBlock {

	public Matrice_clear (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	//@Override
		public String toCode() throws SocketNullException, SubroutineNotDeclaredException
		{
			String Din ;
			String Cs ;
			String Clk ;

			
			TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
			Din = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
			Cs = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(2);
			Clk = translatorBlock.toCode();
			
			String ret = "mesLeds"+Din+Cs+Clk+".clear();";
			
			return codePrefix + ret + codeSuffix;
			
			
		
		}
}
