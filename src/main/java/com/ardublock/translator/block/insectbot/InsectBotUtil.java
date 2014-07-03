package com.ardublock.translator.block.insectbot;

import com.ardublock.translator.Translator;

public class InsectBotUtil
{
	public static void setupEnv(Translator translator)
	{
		translator.addHeaderFile("Servo.h");
		translator.addHeaderFile("InsectBot.h");
		
		translator.addDefinitionCommand("InsectBot insect;");
	}
}
