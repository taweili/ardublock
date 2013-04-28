package com.ardublock.translator.block;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.ardublock.translator.Translator;
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
