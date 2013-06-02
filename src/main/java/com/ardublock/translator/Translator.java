package com.ardublock.translator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import edu.mit.blocks.codeblocks.Block;
import edu.mit.blocks.workspace.Workspace;
import com.ardublock.translator.adaptor.BlockAdaptor;
import com.ardublock.translator.adaptor.OpenBlocksAdaptor;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.TranslatorBlockFactory;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNotDeclatedException;


public class Translator
{
	private static final String variablePrefix = "_ABVAR_";
		
	private Set<String> headerFileSet;
	private Set<String> definitionSet;
	private Set<String> setupSet;
	private Set<String> functionNameSet;
	private BlockAdaptor blockAdaptor;
	
	private Set<Long> inputPinSet;
	private Set<Long> outputPinSet;
	
	private Map<String, String> numberVariableSet;
	private Map<String, String> booleanVariableSet;
	
	private Workspace workspace;
	
	private int variableCnt;
	public Translator(Workspace ws)
	{
		workspace = ws;
		reset();
	}
	
	public String translate(Long blockId) throws SocketNullException
	{
		reset();
		
		TranslatorBlockFactory translatorBlockFactory = new TranslatorBlockFactory();
		Block block = workspace.getEnv().getBlock(blockId);
		
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
		
		if (!functionNameSet.isEmpty())
		{
			for (String command:functionNameSet)
			{
				headerCommand += "void " + command  + "(void);\n";
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
		functionNameSet = new HashSet<String>();
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
	
	public void addFunctionName(String functionName) throws SubroutineNotDeclaredException
	{
		if (functionName.equals("loop") ||functionName.equals("setup") || functionNameSet.contains(functionName))
		{
			throw new SubroutineNotDeclaredException();
		}
		//TODO
		add...
	}
	
	
	public String buildVariableName()
	{
		return buildVariableName("");
	}
	
	public String buildVariableName(String reference)
	{
		variableCnt = variableCnt + 1;
		String varName = variablePrefix + variableCnt + "_";
		int i;
		for (i=0; i<reference.length(); ++i)
		{
			char c = reference.charAt(i);
			if (Character.isLetter(c) || Character.isDigit(c) || (c == '_'))
			{
				varName = varName + c;
			}
		}
		return varName;
	}
	
	public Workspace getWorkspace() {
		return workspace;
	}
	
	public Block getBlock(Long blockId) {
		return workspace.getEnv().getBlock(blockId);
	}
}
