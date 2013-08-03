package com.ardublock.translator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.mit.blocks.codeblocks.Block;
import edu.mit.blocks.workspace.Workspace;
import com.ardublock.translator.adaptor.BlockAdaptor;
import com.ardublock.translator.adaptor.OpenBlocksAdaptor;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.TranslatorBlockFactory;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNameDuplicatedException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;


public class Translator
{
	private static final String variablePrefix = "_ABVAR_";
		
	private Set<String> headerFileSet;
	private Set<String> definitionSet;
	private Set<String> setupSet;
	private Set<String> functionNameSet;
	private BlockAdaptor blockAdaptor;
	
	private Set<String> inputPinSet;
	private Set<String> outputPinSet;
	
	private Map<String, String> numberVariableSet;
	private Map<String, String> booleanVariableSet;
	private List<TranslatorBlock> doAfterBlocks;
	
	private Map<String, String> internalVars;
	
	private Workspace workspace;
	
	private int variableCnt;
	public Translator(Workspace ws)
	{
		workspace = ws;
		reset();
	}
	
	public String genreateHeaderCommand()
	{
		StringBuilder headerCommand = new StringBuilder();
		
		if (!headerFileSet.isEmpty())
		{
			for (String file:headerFileSet)
			{
				headerCommand.append("#include <" + file + ">\n");
			}
			headerCommand.append("\n");
		}
		
		if (!definitionSet.isEmpty())
		{
			for (String command:definitionSet)
			{
				headerCommand.append(command + "\n");
			}
			headerCommand.append("\n");
		}
		
		if (!functionNameSet.isEmpty())
		{
			for (String functionName:functionNameSet)
			{
				headerCommand.append("void " + functionName + "();\n");
			}
			headerCommand.append("\n");
		}
		
		headerCommand.append("void setup()\n{\n");
		
		if (!inputPinSet.isEmpty())
		{
			for (String pinNumber:inputPinSet)
			{
				headerCommand.append("pinMode( " + pinNumber + " , INPUT);\n");
			}
		}
		if (!outputPinSet.isEmpty())
		{
			for (String pinNumber:outputPinSet)
			{
				headerCommand.append("pinMode( " + pinNumber + " , OUTPUT);\n");
			}
		}
		
		if (!setupSet.isEmpty())
		{
			for (String command:setupSet)
			{
				headerCommand.append(command + "\n");
			}
		}
		
		headerCommand.append("}\n\n");
		return headerCommand.toString();
	}
	
	public String translate(Long blockId) throws SocketNullException, SubroutineNotDeclaredException
	{
		
		
		TranslatorBlockFactory translatorBlockFactory = new TranslatorBlockFactory();
		Block block = workspace.getEnv().getBlock(blockId);
		
		TranslatorBlock rootTranslatorBlock = translatorBlockFactory.buildTranslatorBlock(this, blockId, block.getGenusName(), "", "", block.getBlockLabel());
		
		String code = rootTranslatorBlock.toCode();
		
		for (TranslatorBlock translatorBlock : doAfterBlocks)
		{
			translatorBlock.afterTranslation();
		}
		
		return code;
	}
	
	public BlockAdaptor getBlockAdaptor()
	{
		return blockAdaptor;
	}
	
	public void reset()
	{
		headerFileSet = new LinkedHashSet<String>();
		definitionSet = new LinkedHashSet<String>();
		definitionSet = new LinkedHashSet<String>();
		setupSet = new LinkedHashSet<String>();
		functionNameSet = new LinkedHashSet<String>();
		inputPinSet = new LinkedHashSet<String>();
		outputPinSet = new LinkedHashSet<String>();
		
		numberVariableSet = new HashMap<String, String>();
		booleanVariableSet = new HashMap<String, String>();
		
		doAfterBlocks = new LinkedList<TranslatorBlock>();
		
		internalVars = new HashMap<String, String>();
		
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
	
	public void addInputPin(String pinNumber)
	{
		inputPinSet.add(pinNumber);
	}
	
	public void addOutputPin(String pinNumber)
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
	
	public void addFunctionName(Long blockId, String functionName) throws SubroutineNameDuplicatedException
	{
		if (functionName.equals("loop") ||functionName.equals("setup") || functionNameSet.contains(functionName))
		{
			throw new SubroutineNameDuplicatedException(blockId);
		}
		
		functionNameSet.add(functionName);
	}
	
	public boolean containFunctionName(String name)
	{
		return functionNameSet.contains(name.trim());
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

	public void addDoAfter(TranslatorBlock translatorBlock)
	{
		doAfterBlocks.add(translatorBlock);
	}
	
	public void setInternalVar(String name, String value)
	{
		internalVars.put(name, value);
	}
	
	public String getInternalVar(String name)
	{
		return internalVars.get(name);
	}
}
