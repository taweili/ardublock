package com.ardublock.translator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import edu.mit.blocks.codeblocks.Block;
import com.ardublock.translator.adaptor.BlockAdaptor;
import com.ardublock.translator.adaptor.OpenBlocksAdaptor;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.TranslatorBlockFactory;


public class Translator
{
	private static final String variablePrefix = "__ARDUBLOCK_VARIABLE_AUTO_PREFIX_";
		
	private Set<String> headerFileSet;
	private Set<String> definitionSet;
	private Set<String> setupSet;
	private BlockAdaptor blockAdaptor;
	
	private Set<Long> inputPinSet;
	private Set<Long> outputPinSet;
	
	private Map<String, String> numberVariableSet;
	private Map<String, String> booleanVariableSet;
	
	private int variableCnt;
	public Translator()
	{
		reset();
	}
	
	public String translate(Long blockId)
	{
		reset();
		
		TranslatorBlockFactory translatorBlockFactory = new TranslatorBlockFactory();
		Block block = Block.getBlock(blockId);
		
		TranslatorBlock rootTranslatorBlock = translatorBlockFactory.buildTranslatorBlock(this, blockId, block.getGenusName(), block.getBlockLabel(), "", "");
		
		String loopCommand = rootTranslatorBlock.toCode();
		String headerCommand = "";
		if (!headerFileSet.isEmpty())
		{
			for (String file:headerFileSet)
			{
				headerCommand = headerCommand  + "#include <" + file + ">\n";
			}
			headerCommand = headerCommand + "\n";
		}
		
		if (!definitionSet.isEmpty())
		{
			for (String command:definitionSet)
			{
				headerCommand = headerCommand + command + "\n"; 
			}
			headerCommand = headerCommand + "\n";
		}
		
		headerCommand = headerCommand + "void setup()\n{\n";
		
		if (!inputPinSet.isEmpty())
		{
			for (Long pinNumber:inputPinSet)
			{
				headerCommand = headerCommand + "pinMode( " + pinNumber + " , INPUT);\n";
			}
		}
		if (!outputPinSet.isEmpty())
		{
			for (Long pinNumber:outputPinSet)
			{
				headerCommand = headerCommand + "pinMode( " + pinNumber + " , OUTPUT);\n";
			}
		}
		
		if (!setupSet.isEmpty())
		{
			for (String command:setupSet)
			{
				headerCommand = headerCommand + command + "\n";
			}
		}
		
		headerCommand = headerCommand + "}\n\n";
		
		String program = headerCommand + loopCommand;
		return program;
	}
	
	public BlockAdaptor getBlockAdaptor()
	{
		return blockAdaptor;
	}
	
	private void reset()
	{
		headerFileSet = new HashSet<String>();
		definitionSet = new HashSet<String>();
		setupSet = new HashSet<String>();
		inputPinSet = new HashSet<Long>();
		outputPinSet = new HashSet<Long>();
		
		numberVariableSet = new HashMap<String, String>();
		booleanVariableSet = new HashMap<String, String>();
		
		blockAdaptor = buildOpenBlocksAdaptor();
		
		variableCnt = 0;
	}
	
	private BlockAdaptor buildOpenBlocksAdaptor()
	{
		return new OpenBlocksAdaptor();
	}
	
	public void addHeaderFile(String headerFile)
	{
		headerFileSet.add(headerFile);
	}
	
	public void addSetupCommand(String command)
	{
		setupSet.add(command);
	}
	
	public void addDefinitionCommand(String command)
	{
		definitionSet.add(command);
	}
	
	public void addInputPin(Long pinNumber)
	{
		inputPinSet.add(pinNumber);
	}
	
	public void addOutputPin(Long pinNumber)
	{
		outputPinSet.add(pinNumber);
	}
	
	public String buildVariableName()
	{
		variableCnt = variableCnt + 1;
		return variablePrefix + variableCnt;
	}
	
	public String getNumberVariable(String userVarName)
	{
		return numberVariableSet.get(userVarName);
	}
	
	public String getBooleanVariable(String userVarName)
	{
		return booleanVariableSet.get(userVarName);
	}
	
	public void addNumberVariable(String userVarName, String internalName)
	{
		numberVariableSet.put(userVarName, internalName);
	}
	
	public void addBooleanVariable(String userVarName, String internalName)
	{
		booleanVariableSet.put(userVarName, internalName);
	}
}
