package com.ardublock.translator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ardublock.translator.adaptor.BlockAdaptor;
import com.ardublock.translator.adaptor.OpenBlocksAdaptor;
import com.ardublock.translator.block.TranslatorBlock;
import com.ardublock.translator.block.TranslatorBlockFactory;
import com.ardublock.translator.block.exception.SocketNullException;
import com.ardublock.translator.block.exception.SubroutineNameDuplicatedException;
import com.ardublock.translator.block.exception.SubroutineNotDeclaredException;

import edu.mit.blocks.codeblocks.Block;
import edu.mit.blocks.workspace.Workspace;

public class Translator
{
	private static final String variablePrefix = "_ABVAR_";

	private Set<String> headerFileSet;
	private Set<String> definitionSet;
	private List<String> setupCommand;
	private Set<String> functionNameSet;
	private Set<TranslatorBlock> bodyTranslatreFinishCallbackSet;
	private BlockAdaptor blockAdaptor;
	
	private Set<Long> inputPinSet;
	private Set<Long> outputPinSet;
	
	private Map<String, String> numberVariableSet;
	private Map<String, String> booleanVariableSet;
	
	private Workspace workspace;
	
	private String rootBlockName;
	
	private int variableCnt;
	private boolean isScoopProgram;

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
		
		return headerCommand.toString() + generateSetupFunction();
	}
	
	public String generateSetupFunction()
	{
		StringBuilder setupFunction = new StringBuilder();
		setupFunction.append("void setup()\n{\n");
		
		if (!inputPinSet.isEmpty())
		{
			for (Long pinNumber:inputPinSet)
			{
				setupFunction.append("pinMode( " + pinNumber + " , INPUT);\n");
			}
		}
		if (!outputPinSet.isEmpty())
		{
			for (Long pinNumber:outputPinSet)
			{
				setupFunction.append("pinMode( " + pinNumber + " , OUTPUT);\n");
			}
		}
		
		if (!setupCommand.isEmpty())
		{
			for (String command:setupCommand)
			{
				setupFunction.append(command + "\n");
			}
		}
		
		setupFunction.append("}\n\n");
		
		return setupFunction.toString();
	}
	
	public String translate(Long blockId) throws SocketNullException, SubroutineNotDeclaredException
	{
		TranslatorBlockFactory translatorBlockFactory = new TranslatorBlockFactory();
		Block block = workspace.getEnv().getBlock(blockId);
		TranslatorBlock rootTranslatorBlock = translatorBlockFactory.buildTranslatorBlock(this, blockId, block.getGenusName(), "", "", block.getBlockLabel());
		return rootTranslatorBlock.toCode();
	}
	
	public BlockAdaptor getBlockAdaptor()
	{
		return blockAdaptor;
	}
	
	public void reset()
	{
		headerFileSet = new HashSet<String>();
		definitionSet = new HashSet<String>();
		setupCommand = new LinkedList<String>();
		functionNameSet = new HashSet<String>();
		inputPinSet = new HashSet<Long>();
		outputPinSet = new HashSet<Long>();
		bodyTranslatreFinishCallbackSet = new HashSet<TranslatorBlock>();
		
		numberVariableSet = new HashMap<String, String>();
		booleanVariableSet = new HashMap<String, String>();
		
		blockAdaptor = buildOpenBlocksAdaptor();
		
		variableCnt = 0;
		
		rootBlockName = null;
		isScoopProgram = false;
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
		if (!setupCommand.contains(command))
		{
			setupCommand.add(command);
		}
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
	
	public void registerBodyTranslateFinishCallback(TranslatorBlock translatorBlock)
	{
		bodyTranslatreFinishCallbackSet.add(translatorBlock);
	}

	public void beforeGenerateHeader() {
		for (TranslatorBlock translatorBlock : bodyTranslatreFinishCallbackSet)
		{
			translatorBlock.onTranslateBodyFinished();
		}
		
	}

	public String getRootBlockName() {
		return rootBlockName;
	}

	public void setRootBlockName(String rootBlockName) {
		this.rootBlockName = rootBlockName;
	}

	public boolean isScoopProgram() {
		return isScoopProgram;
	}

	public void setScoopProgram(boolean isScoopProgram) {
		this.isScoopProgram = isScoopProgram;
	}
}
