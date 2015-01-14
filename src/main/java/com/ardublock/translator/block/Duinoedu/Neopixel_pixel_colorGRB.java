package com.ardublock.translator.block.Duinoedu;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Neopixel_pixel_colorGRB  extends TranslatorBlock {

	public Neopixel_pixel_colorGRB (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	//@Override
		public String toCode() throws SocketNullException, SubroutineNotDeclaredException
		{
			String Pin ;
			String Pixel_Nb;
			String Red_start;
			String Blue_start;
			String Green_start;
			String Nb_Led;
			String Step;
			String Red_end;
			String Blue_end;
			String Green_end;
			
			TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
			Pin = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
			Pixel_Nb = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(2);
			Red_start = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(3);
			Green_start = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(4);
			Blue_start = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(5);
			Nb_Led = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(6);
			Step = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(7);
			Red_end = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(8);
			Green_end = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(9);
			Blue_end = translatorBlock.toCode();
			
			
			String ret = "monRuban_pin"+Pin+".preparerPixel("+Pixel_Nb+","+Green_start+" ,"+Red_start+" ,"+Blue_start+" ,"+Nb_Led+" ,"+Step+" ,"+Green_end+" ,"+Red_end+" ,"+Blue_end+" );\n";
			
			return codePrefix + ret + codeSuffix;
				
		}
}
