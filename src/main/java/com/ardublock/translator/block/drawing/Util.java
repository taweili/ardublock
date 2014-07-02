package com.ardublock.translator.block.drawing;

import com.ardublock.translator.Translator;



public class Util
{
	public static void setupEnv(Translator translator)
	{
		
		translator.addHeaderFile("public.h");
		translator.addHeaderFile("common.h");
		translator.addHeaderFile("Timer.h");
		translator.addHeaderFile("Player.h");
		translator.addHeaderFile("iDrawing.h");
	}
}
