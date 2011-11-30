package com.ardublock.translator.block;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.tinker.TinkerAccmeterBlock;
import com.ardublock.translator.block.tinker.TinkerButtonBlock;
import com.ardublock.translator.block.tinker.TinkerHallBlock;
import com.ardublock.translator.block.tinker.TinkerLDRBlock;
import com.ardublock.translator.block.tinker.TinkerLEDBlock;
import com.ardublock.translator.block.tinker.TinkerLEDPwmBlock;
import com.ardublock.translator.block.tinker.TinkerLinearPotentiometerBlock;
import com.ardublock.translator.block.tinker.TinkerMosfetBlock;
import com.ardublock.translator.block.tinker.TinkerMosfetPwmBlock;
import com.ardublock.translator.block.tinker.TinkerRelayBlock;
import com.ardublock.translator.block.tinker.TinkerRotaryPotentiometerBlock;
import com.ardublock.translator.block.tinker.TinkerServoBlock;
import com.ardublock.translator.block.tinker.TinkerThermistorBlock;
import com.ardublock.translator.block.tinker.TinkerTiltBlock;
import com.ardublock.translator.block.tinker.TinkerTouchBlock;
import com.ardublock.translator.block.xinchejian.XinchejianDigitalReadBlock;
import com.ardublock.translator.block.xinchejian.XinchejianDigitalWriteBlock;
import com.ardublock.translator.block.xinchejian.XinchejianMotorBackwardBlock;
import com.ardublock.translator.block.xinchejian.XinchejianMotorForwardBlock;

public class TranslatorBlockFactory
{
	public TranslatorBlock buildTranslatorBlock(Translator translator, Long blockId, String blockName, String codePrefix, String codeSuffix, String label)
	{
//		System.out.println("block name : " + blockName + " captured");
		
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
			return new TrueBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		
		if (blockName.equals("and"))
		{
			return new AndBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		if (blockName.equals("not"))
		{
			return new NotBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		if (blockName.equals("or"))
		{
			return new OrBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		if (blockName.equals("digital-high"))
		{
			return new DigitalHighBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		if (blockName.equals("digital-low"))
		{
			return new DigitalLowBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		if (blockName.equals("digital-on"))
		{
			return new DigitalOnBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		if (blockName.equals("digital-off"))
		{
			return new DigitalOffBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		if (blockName.equals("false"))
		{
			return new FalseBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		if (blockName.equals("if"))
		{
			return new IfBlock(blockId, translator);
		}
		if (blockName.equals("while"))
		{
			return new WhileBlock(blockId, translator);
		}
		if (blockName.equals("repeat_times"))
		{
			return new RepeatTimesBlock(blockId, translator);
		}
		
		//const number
		if (blockName.equals("number"))
		{
			return new NumberBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		if (blockName.equals("message"))
		{
			return new MessageBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		if (blockName.equals("glue_sn"))
		{
			return new GlueSNBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		if (blockName.equals("glue_sb"))
		{
			return new GlueSBBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		
		//Pin
		if (blockName.equals("pin-read-analog"))
		{
			return new PinReadAnalogBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		if (blockName.equals("pin-read-digital"))
		{
			return new PinReadDigitalBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		if (blockName.equals("pin-write-analog"))
		{
			return new PinWriteAnalogBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		if (blockName.equals("pin-write-digital"))
		{
			return new PinWriteDigitalBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		if (blockName.equals("servo"))
		{
			return new ServoBlock(blockId, translator);
		}
		if (blockName.equals("ultrasonic"))
		{
			return new UltrasonicBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		if (blockName.equals("setter_variable_number"))
		{
			return new SetterVariableNumberBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		if (blockName.equals("variable_number"))
		{
			return new VariableNumberBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		if (blockName.equals("setter_variable_digital"))
		{
			return new SetterVariableDigitalBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		if (blockName.equals("variable_digital"))
		{
			return new VariableDigitalBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		
		//Math

		if (blockName.equals("addition"))
		{
			return new AdditionBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		if (blockName.equals("subtraction"))
		{
			return new SubtractionBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		if (blockName.equals("multiplication"))
		{
			return new MultiplicationBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		if (blockName.equals("division"))
		{
			return new DivisionBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		if (blockName.equals("modulo"))
		{
			return new ModuloBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		
		if (blockName.equals("greater"))
		{
			return new GreaterBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		if (blockName.equals("less"))
		{
			return new LessBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		if (blockName.equals("equal"))
		{
			return new EqualBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		if (blockName.equals("greater_equal"))
		{
			return new GreaterEqualBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		if (blockName.equals("less_equal"))
		{
			return new LessEqualBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		if (blockName.equals("not_equal"))
		{
			return new NotEqualBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		
		if (blockName.equals("min"))
		{
			return new MinBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		if (blockName.equals("max"))
		{
			return new MaxBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		if (blockName.equals("abs"))
		{
			return new AbsBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		if (blockName.equals("pow"))
		{
			return new PowBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		if (blockName.equals("sqrt"))
		{
			return new SqrtBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		if (blockName.equals("sin"))
		{
			return new SinBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		if (blockName.equals("cos"))
		{
			return new CosBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		if (blockName.equals("tan"))
		{
			return new TanBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		
		
		
		//Utility
		if (blockName.equals("delay"))
		{
			return new DelayBlock(blockId, translator);
		}
		if (blockName.equals("random"))
		{
			return new RandomBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		if (blockName.equals("serial_println"))
		{
			return new SerialPrintlnBlock(blockId, translator);
		}
		if (blockName.equals("map_common"))
		{
			return new MapCommonBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		if (blockName.equals("map"))
		{
			return new MapBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		if (blockName.equals("delay_microseconds"))
		{
			return new DelayMicrosecondsBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		if (blockName.equals("millis"))
		{
			return new MillisBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		if (blockName.equals("wire_read"))
		{
			return new WireReadBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		if (blockName.equals("wire_write"))
		{
			return new WireWriteBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		if (blockName.equals("wire_isread"))
		{
			return new WireIsReadBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		
		//brick
		if (blockName.equals("Tinker_LED"))
		{
			return new TinkerLEDBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		if (blockName.equals("Tinker_Mosfet"))
		{
			return new TinkerMosfetBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		if (blockName.equals("Tinker_Relay"))
		{
			return new TinkerRelayBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		
		if (blockName.equals("Tinker_Button"))
		{
			return new TinkerButtonBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		
		if (blockName.equals("Tinker_Accmeter"))
		{
			return new TinkerAccmeterBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		if (blockName.equals("Tinker_Hall"))
		{
			return new TinkerHallBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		if (blockName.equals("Tinker_LDR"))
		{
			return new TinkerLDRBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		if (blockName.equals("Tinker_LinearPotentiometer"))
		{
			return new TinkerLinearPotentiometerBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		if (blockName.equals("Tinker_RotaryPotentiometer"))
		{
			return new TinkerRotaryPotentiometerBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		if (blockName.equals("Tinker_Thermistor"))
		{
			return new TinkerThermistorBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		if (blockName.equals("Tinker_Touch"))
		{
			return new TinkerTouchBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		if (blockName.equals("Tinker_Tilt"))
		{
			return new TinkerTiltBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		if (blockName.equals("Tinker_LED_pwm"))
		{
			return new TinkerLEDPwmBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		if (blockName.equals("Tinker_Mosfet_pwm"))
		{
			return new TinkerMosfetPwmBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		if (blockName.equals("Tinker_Servo"))
		{
			return new TinkerServoBlock(blockId, translator);
		}
		
		//Xinchejian
		if (blockName.equals("xcj_motor_forward"))
		{
			return new XinchejianMotorForwardBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		if (blockName.equals("xcj_motor_backward"))
		{
			return new XinchejianMotorBackwardBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		if (blockName.endsWith("xcj_ms_digital_write"))
		{
			return new XinchejianDigitalWriteBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		if (blockName.equals("xcj_ms_digital_read"))
		{
			return new XinchejianDigitalReadBlock(blockId, translator, codePrefix, codeSuffix, label);
		}
		
		System.err.println(blockName + " not found!");
		
		return null;
	}
}
