package com.ardublock.translator.block;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import com.ardublock.translator.Translator;
import com.ardublock.util.PropertiesReader;

public class TranslatorBlockFactory
{
	private static final String BLOCK_MAPPING = "com/ardublock/block/block-mapping.properties";
	
	private Map<String, String> shortClassName;
	
	public TranslatorBlockFactory()
	{
		shortClassName = new HashMap<String, String>();
		shortClassName.put("analogInput", "com.ardublock.translator.block.AnalogInputBlock");
		shortClassName.put("digitalInput", "com.ardublock.translator.block.DigitalInputBlock");
		shortClassName.put("digitalInputPullup","com.ardublock.translator.block.DigitalInputPullBlock");
		shortClassName.put("analogOutput", "com.ardublock.translator.block.AnalogOutputBlock");
		shortClassName.put("digitalOutput", "com.ardublock.translator.block.DigitalOutputBlock");
		shortClassName.put("inversedDigitalInput", "com.ardublock.translator.block.InversedDigitalInputBlock");
		shortClassName.put("inversedDigitalOutput", "com.ardublock.translator.block.InversedDigitalOutputBlock");
		shortClassName.put("inversedAnalogOutput", "com.ardublock.translator.block.InversedAnalogOutputBlock");
		shortClassName.put("servo", "com.ardublock.translator.block.ServoBlock");
		shortClassName.put("tone", "com.ardublock.translator.block.ToneBlock");
		shortClassName.put("toneTime", "com.ardublock.translator.block.ToneTimeBlock");
		shortClassName.put("noTone", "com.ardublock.translator.block.NoToneBlock");
	}
	
	
	public TranslatorBlock buildTranslatorBlock(Translator translator, Long blockId, String blockName, String codePrefix, String codeSuffix, String label)
	{
//		System.out.println("block name : " + blockName + " captured");
		
		String className = PropertiesReader.getValue(blockName, BLOCK_MAPPING);
		//System.out.println("className: " + className);
		String longName = shortClassName.get(className);
		if (longName != null)
		{
			className = longName;
		}
		
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
