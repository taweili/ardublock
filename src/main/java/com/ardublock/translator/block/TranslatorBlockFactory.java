package com.ardublock.translator.block;

import com.ardublock.translator.Translator;

public class TranslatorBlockFactory
{
	public TranslatorBlock buildTranslatorBlock(Translator translator, Long blockId, String blockName, String label)
	{
		System.out.println("block name : " + blockName + " captured");
		
		//Arduino
		if (blockName.equals("loop"))
		{
			return new LoopBlock(blockId, translator);
		}
		
		
		if (blockName.equals("ifelse"))
		{
			return new IfelseBlock(blockId, translator);
		}
		if (blockName.equals("true"))
		{
			return new TrueBlock(blockId, translator);
		}
		
		if (blockName.equals("and"))
		{
			return new AndBlock(blockId, translator);
		}
		if (blockName.equals("not"))
		{
			return new NotBlock(blockId, translator);
		}
		if (blockName.equals("or"))
		{
			return new OrBlock(blockId, translator);
		}
		if (blockName.equals("digital-high"))
		{
			return new DigitalHighBlock(blockId, translator);
		}
		if (blockName.equals("digital-low"))
		{
			return new DigitalHighBlock(blockId, translator);
		}
		if (blockName.equals("false"))
		{
			return new FalseBlock(blockId, translator);
		}
		if (blockName.equals("if"))
		{
			return new IfBlock(blockId, translator);
		}
		if (blockName.equals("while"))
		{
			return new WhileBlock(blockId, translator);
		}
		
		if (blockName.equals("number"))
		{
			return new NumberBlock(blockId, label, translator);
		}
		
		//Pin
		if (blockName.equals("pin-read-analog"))
		{
			return new PinReadAnalogBlock(blockId, translator);
		}
		if (blockName.equals("pin-read-digital"))
		{
			return new PinReadDigitalBlock(blockId, translator);
		}
		if (blockName.equals("pin-write-analog"))
		{
			return new PinWriteAnalogBlock(blockId, translator);
		}
		if (blockName.equals("pin-write-digital"))
		{
			return new PinWriteDigitalBlock(blockId, translator);
		}
		if (blockName.equals("servo"))
		{
			return new ServoBlock(blockId, translator);
		}
		
		//Math

		if (blockName.equals("addition"))
		{
			return new AdditionBlock(blockId, translator);
		}
		if (blockName.equals("subtraction"))
		{
			return new SubtractionBlock(blockId, translator);
		}
		if (blockName.equals("multiplication"))
		{
			return new MultiplicationBlock(blockId, translator);
		}
		if (blockName.equals("division"))
		{
			return new DivisionBlock(blockId, translator);
		}
		if (blockName.equals("modulo"))
		{
			return new ModuloBlock(blockId, translator);
		}
		if (blockName.equals("min"))
		{
			return new MinBlock(blockId, translator);
		}
		if (blockName.equals("max"))
		{
			return new MaxBlock(blockId, translator);
		}
		if (blockName.equals("abs"))
		{
			return new AbsBlock(blockId, translator);
		}
		if (blockName.equals("pow"))
		{
			return new PowBlock(blockId, translator);
		}
		if (blockName.equals("sqrt"))
		{
			return new SqrtBlock(blockId, translator);
		}
		if (blockName.equals("sin"))
		{
			return new SinBlock(blockId, translator);
		}
		if (blockName.equals("cos"))
		{
			return new CosBlock(blockId, translator);
		}
		if (blockName.equals("tan"))
		{
			return new TanBlock(blockId, translator);
		}
		
		
		
		//Utility
		if (blockName.equals("delay"))
		{
			return new DelayBlock(blockId, translator);
		}
		if (blockName.equals("random"))
		{
			return new RandomBlock(blockId, translator);
		}
		
		
		System.err.println(blockName + " not found!");
		
		return null;
	}
}
