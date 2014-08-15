package com.ardublock.translator.block.Duinoedu;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class Matrice_write  extends TranslatorBlock {

	public Matrice_write (Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	
	//@Override
		public String toCode() throws SocketNullException, SubroutineNotDeclaredException
		{
			String Din ;
			String Cs ;
			String Clk ;
			String X ;
			String Y;
			String Level;
			
			TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
			Din = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
			Cs = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(2);
			Clk = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(3);
			X = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(4);
			Y = translatorBlock.toCode();
			translatorBlock = this.getRequiredTranslatorBlockAtSocket(5);
			Level = translatorBlock.toCode();
			
			
			translator.addHeaderFile("Sprite.h");
			translator.addDefinitionCommand("//libraries http://duinoedu.com/dl/lib/dupont/sprite/");
			translator.addHeaderFile("Matrix.h");
			translator.addDefinitionCommand("//libraries http://duinoedu.com/dl/lib/dupont/matrix/\n"+
			"Matrix mesLeds"+Din+Cs+Clk+" = Matrix("+Din+","+Cs+","+Clk+",1);\n"+
			"int X[] = {0,7,6,5,4,3,2,1};\nint Y[] = {7,6,5,4,3,2,1,0};");
			translator.addSetupCommand("mesLeds"+Din+Cs+Clk+".clear();");
			
			String ret = "mesLeds"+Din+Cs+Clk+".write(X["+X+"],Y["+Y+"],"+Level+");";
			
			return codePrefix + ret + codeSuffix;
			
			
		
		}
}
