package com.ardublock.translator.block;

import java.util.HashSet;
import java.util.Set;

import com.ardublock.translator.Translator;

import edu.mit.blocks.codeblocks.Block;
import edu.mit.blocks.codeblocks.BlockConnector;
import edu.mit.blocks.renderable.RenderableBlock;
import edu.mit.blocks.codeblocks.Variable;

public class SetGlobalVarBlock extends TranslatorBlock
{
	protected SetGlobalVarBlock(Long blockId, Translator translator, String codePrefix, String codeSuffix, String label)
	{
		super(blockId, translator, codePrefix, codeSuffix, label);
	}

	public String toCode()
	{
		String internalVariableName = translator.getBooleanVariable(label.substring(4));
		String varName = translator.getBlock(blockId).getBlockLabel();
		String labelChild = null;	// valeur de la variable				
		String kindChild = null;
		
		Iterable<RenderableBlock> allRenderableBlock = translator.getWorkspace().getRenderableBlocks();//
		Set<RenderableBlock> headerBlockSet = new HashSet<RenderableBlock>();

		//all renderable blocks listing
		for (RenderableBlock renderableBlock:allRenderableBlock)//
		{
			Block block = renderableBlock.getBlock();
			boolean titi = block.isVariableDeclBlock();
			
			//search first block of each stacking
			if (!block.hasPlug() && (Block.NULL.equals(block.getBeforeBlockID())))
			{
				if(block.getBlockLabel().equals(varName.substring(4)))		// substring removes the four letters "set " at the begenning of the varName
				{
					BlockConnector socketConnector = block.getSocketAt(0);
					Block blockChild = translator.getBlock(socketConnector.getBlockID());
					kindChild = socketConnector.getKind();
					
					if (blockChild != null) {
						labelChild = blockChild.getBlockLabel();	// ok, donne la valeur de la variable boolenne
					}
				}
			}
		}
		if (internalVariableName == null)
		{
			internalVariableName = translator.buildVariableName(label);
			String definition = internalVariableName;
			//Variable(internalVariableName, kindChild, "global");
			//Set<Variable> set =  new HashSet<Variable>() ;

			if (kindChild.contains("boolean")) {
				definition = "boolean " + definition;
				translator.addBooleanVariable(label, internalVariableName);
			}
			else if (kindChild.contains("number")) {
				definition = "int " + definition;
				translator.addNumberVariable(label, internalVariableName);
			}
			else if (kindChild.contains("string")) {
				definition = "String " + definition;
				translator.addStringVariable(label, internalVariableName);
			}
			else {
				
			}
			if (labelChild != null) {
				definition = definition + " = " + labelChild;
			}
			translator.addDefinitionCommand(definition + ";");
		}
		String ret = internalVariableName;
		return codePrefix + ret + codeSuffix;
	}

}
