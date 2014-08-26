package com.ardublock.translator.block.keenlon;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.NumberBlock;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.exception.BlockException;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

public class TFTPrintBlock extends TranslatorBlock
{
	public TFTPrintBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}
	  public static String replace(String strSource, String strFrom, String strTo) {    
		    if (strSource == null) {        
		      return null;    
		    }  
		    int i = 0;
		    if ((i = strSource.indexOf(strFrom, i)) >= 0) {
		      char[] cSrc = strSource.toCharArray();
		      char[] cTo = strTo.toCharArray();
		      int len = strFrom.length();  
		      StringBuffer buf = new StringBuffer(cSrc.length);  
		      buf.append(cSrc, 0, i).append(cTo);
		      i += len;    
		      int j = i;       
		      while ((i = strSource.indexOf(strFrom, i)) > 0) {  
		        buf.append(cSrc, j, i - j).append(cTo);   
		        i += len;  
		        j = i;        
		      }        
		      buf.append(cSrc, j, cSrc.length - j); 
		      return buf.toString(); 
		    } 
		    return strSource;
		  }
	public String toCode() throws SocketNullException, SubroutineNotDeclaredException
	{
		
		translator.addHeaderFile("keenlon.h");		
		translator.addDefinitionCommand("TFT tft;");
		translator.addSetupCommand("tft.init();");
		
		TranslatorBlock translatorBlock = this.getRequiredTranslatorBlockAtSocket(0);
		String line = translatorBlock.toCode();
		translatorBlock = this.getRequiredTranslatorBlockAtSocket(1);
		String str = translatorBlock.toCode();
		str=this.replace(str,"\\\\","\\");
		str=this.replace(str,"\\\"","\"");

		String ret ="tft.printf(" + line + " , " + str;

		ret += ");\n";
		return  ret;						
	}
}
