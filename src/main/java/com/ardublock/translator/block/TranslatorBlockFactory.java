package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.tinker.TinkerAccmeterBlock;
import com.ardublock.translator.block.tinker.TinkerButtonBlock;
import com.ardublock.translator.block.tinker.TinkerHallBlock;
import com.ardublock.translator.block.tinker.TinkerLDRBlock;
import com.ardublock.translator.block.tinker.TinkerLEDBlock;
import com.ardublock.translator.block.tinker.TinkerLinearPotentiometerBlock;
import com.ardublock.translator.block.tinker.TinkerMosfetBlock;
import com.ardublock.translator.block.tinker.TinkerRelayBlock;
import com.ardublock.translator.block.tinker.TinkerRotaryPotentiometerBlock;
import com.ardublock.translator.block.tinker.TinkerThermistorBlock;
import com.ardublock.translator.block.tinker.TinkerTiltBlock;
import com.ardublock.translator.block.tinker.TinkerTouchBlock;

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
		if (blockName.equals("digital-on"))
		{
			return new DigitalOnBlock(blockId, translator);
		}
		if (blockName.equals("digital-off"))
		{
			return new DigitalOffBlock(blockId, translator);
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
		if (blockName.equals("ultrasonic"))
		{
			return new UltrasonicBlock(blockId, translator);
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
		
		
		//brick
		if (blockName.equals("Tinker_LED"))
		{
			return new TinkerLEDBlock(blockId, translator);
		}
		if (blockName.equals("Tinker_Mosfet"))
		{
			return new TinkerMosfetBlock(blockId, translator);
		}
		if (blockName.equals("Tinker_Relay"))
		{
			return new TinkerRelayBlock(blockId, translator);
		}
		
		if (blockName.equals("Tinker_Button"))
		{
			return new TinkerButtonBlock(blockId, translator);
		}
		
		if (blockName.equals("Tinker_Accmeter"))
		{
			return new TinkerAccmeterBlock(blockId, translator);
		}
		if (blockName.equals("Tinker_Hall"))
		{
			return new TinkerHallBlock(blockId, translator);
		}
		if (blockName.equals("Tinker_LDR"))
		{
			return new TinkerLDRBlock(blockId, translator);
		}
		if (blockName.equals("Tinker_LinearPotentiometer"))
		{
			return new TinkerLinearPotentiometerBlock(blockId, translator);
		}
		if (blockName.equals("Tinker_RotaryPotentiometer"))
		{
			return new TinkerRotaryPotentiometerBlock(blockId, translator);
		}
		if (blockName.equals("Tinker_Thermistor"))
		{
			return new TinkerThermistorBlock(blockId, translator);
		}
		if (blockName.equals("Tinker_Touch"))
		{
			return new TinkerTouchBlock(blockId, translator);
		}
		if (blockName.equals("Tinker_Tilt"))
		{
			return new TinkerTiltBlock(blockId, translator);
		}
		
		
		System.err.println(blockName + " not found!");
		
		return null;
	}
}
