package com.ardublock.translator.block;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.ardublock.translator.Translator;
import com.ardublock.translator.block.dfrobot.DfrobotAnalogInputBlock;
import com.ardublock.translator.block.dfrobot.DfrobotAnalogOuptutBlock;
import com.ardublock.translator.block.dfrobot.DfrobotDigitalInoutBlock;
import com.ardublock.translator.block.dfrobot.DfrobotDigitalOutputBlock;
import com.ardublock.translator.block.dfrobot.DfrobotLedBlock;
import com.ardublock.translator.block.dfrobot.DfrobotLedPwmBlock;
import com.ardublock.translator.block.dfrobot.DfrobotPushButtonBlock;
import com.ardublock.translator.block.dfrobot.DfrobotServoBlock;
import com.ardublock.translator.block.dfrobot.DfrobotToneBlock;
import com.ardublock.translator.block.dfrobot.DfrobotToneDelayBlock;
import com.ardublock.translator.block.seeedstudio.GroveJoyStickButtonBlock;
import com.ardublock.translator.block.tinker.TinkerAccmeterBlock;
import com.ardublock.translator.block.tinker.TinkerButtonBlock;
import com.ardublock.translator.block.tinker.TinkerHallBlock;
import com.ardublock.translator.block.tinker.TinkerInputPortBlock;
import com.ardublock.translator.block.tinker.TinkerLDRBlock;
import com.ardublock.translator.block.tinker.TinkerLEDBlock;
import com.ardublock.translator.block.tinker.TinkerLEDPwmBlock;
import com.ardublock.translator.block.tinker.TinkerLinearPotentiometerBlock;
import com.ardublock.translator.block.tinker.TinkerMosfetBlock;
import com.ardublock.translator.block.tinker.TinkerMosfetPwmBlock;
import com.ardublock.translator.block.tinker.TinkerOutputPortBlock;
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
import com.ardublock.util.PropertiesReader;

public class TranslatorBlockFactory
{
	private static final String BLOCK_MAPPING = "com/ardublock/block/block-mapping.properties";
	
	public TranslatorBlock buildTranslatorBlock(Translator translator, Long blockId, String blockName, String codePrefix, String codeSuffix, String label)
	{
//		System.out.println("block name : " + blockName + " captured");
		
		String className = PropertiesReader.getValue(blockName, BLOCK_MAPPING);
		System.out.println("className: " + className);
		
		try
		{
			Class blockClass = Class.forName(className);
			Constructor constructor = blockClass.getConstructor(Long.class, Translator.class, String.class, String.class, String.class);
			TranslatorBlock ret = (TranslatorBlock)constructor.newInstance(blockId, translator, codePrefix, codeSuffix, label);
			return ret;
		}
		catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.err.println(blockName + " not suitable class!");
		}		

		System.err.println(blockName + " not found!");
		
		return null;
	}
}
