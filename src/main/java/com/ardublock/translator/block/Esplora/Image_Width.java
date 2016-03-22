package com.ardublock.translator.block.Esplora;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;


public class Image_Width extends TranslatorBlock {

	public Image_Width (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	//@Override
			public String toCode() throws SocketNullException, SubroutineNotDeclaredException
			{
				String PImage;
				
				TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
				PImage = translatorBlock.toCode();
				
				String ret = PImage+".width()";
				
				return codePrefix + ret + codeSuffix;
					
			}
	
}
