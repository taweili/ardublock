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


public class Translator
{
	private static final String variableGlobalPrefix = "G_ABvar_";
	private static final String variableLocalPrefix = "L_ABvar_";
		
	private Set<String> headerFileSet;
	private Set<String> definitionSet;
	private Set<String> setupSet;
	private BlockAdaptor blockAdaptor;
	
	private Set<Long> inputPinSet;
	private Set<Long> outputPinSet;
	
	private Map<String, String> numberVariableSet;
	private Map<String, String> booleanVariableSet;
	private Map<String, String> stringVariableSet;
	
	private Map<String, String> numberLocalVariableSet;
	private Map<String, String> booleanLocalVariableSet;
	private Map<String, String> stringLocalVariableSet;
	
	private Set<String> taskLoopSet;
	private Map<String, String> taskLoopPrioritySet;
	
	private Workspace workspace;
	
	private int variableCnt;
	public Translator(Workspace ws)
	{
		workspace = ws;
		reset();
	}
	
	// code creation for header
	public String translateHeader(Long blockId) throws SocketNullException
	{	
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
	
	if (!taskLoopSet.isEmpty())
	{
		for (String command:taskLoopSet)
		{
			headerCommand = headerCommand + "declareTaskLoopSet( " + command + " )\n"; 
		}
		headerCommand = headerCommand + "\n";
	}
	
	headerCommand = headerCommand + "void setup() {\n";
	
	if (!inputPinSet.isEmpty())
	{
		for (Long pinNumber:inputPinSet)
		{
			headerCommand = headerCommand + "\tpinMode( " + pinNumber + " , INPUT);\n";
		}
	}
	if (!outputPinSet.isEmpty())
	{
		for (Long pinNumber:outputPinSet)
		{
			headerCommand = headerCommand + "\tpinMode( " + pinNumber + " , OUTPUT);\n";
		}
		headerCommand = headerCommand + "\n";
	}
	
	if (!setupSet.isEmpty())
	{
		for (String command:setupSet)
		{
			headerCommand = headerCommand + "\t" + command + "\n";
		}
		headerCommand = headerCommand + "\n";
	}
	
	if (!taskLoopPrioritySet.isEmpty())
	{
		for (String mapTask : taskLoopPrioritySet.keySet()) {
			String mapPriority = taskLoopPrioritySet.get(mapTask);
			headerCommand = headerCommand + "\tcreateTaskLoop( " + mapTask + ", " + mapPriority + " )\n"; 
		}
		headerCommand = headerCommand + "\n";
	}

	headerCommand = headerCommand + "}\n\n";
	return headerCommand;	
	}
	
	// code creation for Loop
	public String translate(Long blockId) throws SocketNullException
	{
		//reset();
		
		TranslatorBlockFactory translatorBlockFactory = new TranslatorBlockFactory();
		Block block = workspace.getEnv().getBlock(blockId);
		
		TranslatorBlock rootTranslatorBlock = translatorBlockFactory.buildTranslatorBlock(this, blockId, block.getGenusName(), block.getBlockLabel(), "", "");
		
		String loopCommand = rootTranslatorBlock.toCode();

		return loopCommand;
	}

	// code creation for Procedure
	public String translateProc(Long blockId) throws SocketNullException
	{
		//reset();
		
		TranslatorBlockFactory translatorBlockFactory = new TranslatorBlockFactory();
		Block block = workspace.getEnv().getBlock(blockId);
		
		TranslatorBlock rootTranslatorBlock = translatorBlockFactory.buildTranslatorBlock(this, blockId, block.getGenusName(), block.getBlockLabel(), "", "");
		
		String procCommand = rootTranslatorBlock.toCode();
		
		return procCommand;
	}

/*	// code creation for Global variables
	public String translateVar(Long blockId) throws SocketNullException
	{
		//reset();
		
		TranslatorBlockFactory translatorBlockFactory = new TranslatorBlockFactory();
		Block block = workspace.getEnv().getBlock(blockId);
		
		TranslatorBlock rootTranslatorBlock = translatorBlockFactory.buildTranslatorBlock(this, blockId, block.getGenusName(), block.getBlockLabel(), "", "");
		
		String varCommand = rootTranslatorBlock.toCode();
		
		return varCommand;
	}*/

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
		stringVariableSet = new HashMap<String, String>();
		
		numberLocalVariableSet = new HashMap<String, String>();
		booleanLocalVariableSet = new HashMap<String, String>();
		stringLocalVariableSet = new HashMap<String, String>();
		
		taskLoopSet = new HashSet<String>();
		taskLoopPrioritySet = new HashMap<String, String>();
		
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
	
	public String getStringVariable(String userVarName)
	{				
		return stringVariableSet.get(userVarName);
	}

	public void addNumberVariable(String userVarName, String internalName)
	{
		numberVariableSet.put(userVarName, internalName);
	}
	
	public void addBooleanVariable(String userVarName, String internalName)
	{
		booleanVariableSet.put(userVarName, internalName);
	}
	
	public void addStringVariable(String userVarName, String internalName)
	{
		stringVariableSet.put(userVarName, internalName);
	}

	public String getNumberLocalVariable(String userVarName)
	{
		return numberLocalVariableSet.get(userVarName);
	}
	
	public String getBooleanLocalVariable(String userVarName)
	{
		return booleanLocalVariableSet.get(userVarName);
	}
	
	public String getStringLocalVariable(String userVarName)
	{				
		return stringLocalVariableSet.get(userVarName);
	}

	public void addNumberLocalVariable(String userVarName, String internalName)
	{
		numberLocalVariableSet.put(userVarName, internalName);
	}
	
	public void addBooleanLocalVariable(String userVarName, String internalName)
	{
		booleanLocalVariableSet.put(userVarName, internalName);
	}
	
	public void addStringLocalVariable(String userVarName, String internalName)
	{
		stringLocalVariableSet.put(userVarName, internalName);
	}

	public void addTaskLoop(String taskLoopName)
	{
		taskLoopSet.add(taskLoopName);
	}
	public void addTaskPriorityLoop(String taskLoopName, String Priority)
	{
		taskLoopPrioritySet.put(taskLoopName, Priority);
	}
	public String getTaskPriorityLoop(String taskLoopName)
	{				
		return taskLoopPrioritySet.get(taskLoopName);
	}

	public String buildVariableName()
	{
		return buildVariableName("");
	}
	
	public String buildVariableName(String reference)
	{
		variableCnt = variableCnt + 1;
		String varName = variableGlobalPrefix + variableCnt + "_";
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
	
	public String buildLocalVariableName()
	{
		return buildLocalVariableName("");
	}
	
	public String buildLocalVariableName(String reference)
	{
		variableCnt = variableCnt + 1;
		String varName = variableLocalPrefix + variableCnt + "_";
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
